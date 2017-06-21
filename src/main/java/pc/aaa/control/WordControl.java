package pc.aaa.control;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import pc.aaa.domain.Resume;
import pc.aaa.domain.enums.ErrorCode;
import pc.aaa.service.ResumeService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wu on 17-6-13.
 */
@Controller
public class WordControl{

    @Autowired
    private ResumeService resumeService;


    @RequestMapping("/downloadword")
    public void testExportWord2(HttpServletResponse res,String resumeid) throws Exception , IOException {

        String tmpFile = "aaa.doc";//word模板
        res.setHeader("content-type", "application/octet-stream");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=Personal resume.doc" );//下载后的word名

        Resume resume=this.resumeService.userresume(resumeid);
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("name", resume.getName());
        datas.put("tel", resume.getTel());
        datas.put("sex",resume.getSex());
        datas.put("hometown", resume.getHometown());
        datas.put("birthday",resume.getBirthday());
        datas.put("email",resume.getEmail());
        datas.put("education",resume.getEducation());
        datas.put("major",resume.getMajor());
        datas.put("school",resume.getSchool());
        datas.put("occupation",resume.getOccupation());
        datas.put("coures",resume.getCoures());
        if (resume.getExperience()!=null) {
            datas.put("experience", resume.getExperience());
        }else {
            datas.put("experience", "无");
        }

        FileInputStream tempFileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:"+tmpFile));
        HWPFDocument document = new HWPFDocument(tempFileInputStream);
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);

        byte[] buff = byteArrayOutputStream.toByteArray();
        OutputStream os = null;
        BufferedInputStream bis = null;
        try {
            os = res.getOutputStream();
            os.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("download success");
    }
}