package pc.aaa.control;

import com.google.gson.Gson;


import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.aaa.domain.Resume;
import pc.aaa.repo.ResumeRepository;
import pc.aaa.service.ResumeService;

import java.io.*;

import java.util.Map;

/**
 * Created by wu on 17-6-22.
 */

@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class PdfControl {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeRepository resumeRepository;

    @RequestMapping("/resume/pdf")
    public void testExportWord2(String resumeid) throws Exception , IOException {

        Resume resume =this.resumeService.userresume(resumeid);

        FileInputStream tempFileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:aaa.pdf"));

        //Initialize PDF document
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(bao);
        PdfDocument pdf = new PdfDocument(new PdfReader(tempFileInputStream),writer);

        //处理中文问题
        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("fill_1").setValue(resume.getName()).setFont(font);
        fields.get("fill_2").setValue(resume.getTel()).setFont(font);
        fields.get("fill_3").setValue(resume.getGender()).setFont(font);
        fields.get("fill_4").setValue(resume.getHometown()).setFont(font);
        fields.get("fill_5").setValue(resume.getBirthday()).setFont(font);
        fields.get("fill_6").setValue(resume.getEmail()).setFont(font);
        fields.get("fill_7").setValue(resume.getEducation()).setFont(font);
        fields.get("fill_8").setValue(resume.getMajor()).setFont(font);
        fields.get("fill_9").setValue(resume.getSchool()).setFont(font);
        fields.get("fill_10").setValue(resume.getOccupation()).setFont(font);
        fields.get("fill_11").setValue(resume.getCoures()).setFont(font);
        fields.get("fill_12").setValue(resume.getExperience()).setFont(font);


        pdf.close();


        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String ACCESS_KEY = "RMVns76oWlTpTDTSADE9F8_Lx7lmpCm6VkbgtNs-";
        String SECRET_KEY = "iph1fxlYUYktFSaI_1jnt6DgJMIKWZKcH2zSBV5h";
        String bucketname = "resume";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
//        try {
        byte[] uploadBytes = bao.toByteArray();//.getBytes("utf-8");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(bucketname);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            resume.setQiniuname(putRet.key);//将key作为简历在七牛云上的名称
            resumeRepository.save(resume);
            System.out.println(putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        System.out.println("upload success");
    }

}
