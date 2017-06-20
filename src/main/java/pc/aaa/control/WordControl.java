package pc.aaa.control;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import pc.aaa.domain.enums.ErrorCode;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wu on 17-6-13.
 */
@Controller
public class WordControl{


    @RequestMapping("/downloadword")
    public void testExportWord2(HttpServletResponse res) throws Exception , IOException {
        String con="这里是内容，测试使"+(char)11+"用POI导出到Word的内容！";
        String tmpFile = "aaa.doc";
        res.setHeader("content-type", "application/octet-stream");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=Personal resume.doc" );

        Map<String, String> datas = new HashMap<String, String>();
        datas.put("name", "标题部份");
//        datas.put("content", "这里是内容，测试使"+(char)11+"用POI导出到Word的内容！");
        datas.put("content",con);
        datas.put("age", "知识林");

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