package pc.aaa.service;

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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pc.aaa.domain.Pdf;
import pc.aaa.domain.Resume;
import pc.aaa.repo.PdfRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 17-6-22.
 */

@Service
public class PdfService {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private UseIdGenerate useIdGenerate;

    public List<Pdf> templastall(String resumeid){
        return this.pdfRepository.findByResumeid(resumeid);
    }

    public void testExportWord2(String resumeid) throws Exception {

        Resume resume = this.resumeService.userresume(resumeid);
        String str[] = {"jdbs.pdf","bgbs.pdf","dqbs.pdf","jjbs.pdf","qxbs.pdf","srbs.pdf"};
        int i = 0;

        while (i < 1) {
        String temp="经典板式";

            FileInputStream tempFileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:"+str[i]));
            if (str[i]=="jdbs.pdf"){
                 temp="经典板式";
            }else if (str[i]=="bgbs.pdf"){
                 temp="表格板式";
            }else if (str[i]=="dqbs.pdf"){
                 temp="大气板式";
            }else if (str[i]=="jjbs.pdf"){
                 temp="极简板式";
            }else if (str[i]=="qxbs.pdf"){
                 temp="清新板式";
            }else if (str[i]=="srbs.pdf"){
                 temp="素雅板式";
            }

            String pdfname=this.useIdGenerate.createid("pdf");
            String newpdf="./pdf/"+pdfname+".pdf";
            PdfDocument pdf = new PdfDocument(new PdfReader(tempFileInputStream), new PdfWriter(newpdf));

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
            fields.get("fill_7").setValue(resume.getQualification()).setFont(font);
            fields.get("fill_8").setValue(resume.getMajor()).setFont(font);
            fields.get("fill_9").setValue(resume.getSchool()).setFont(font);
            fields.get("fill_10").setValue(resume.getOccupation()).setFont(font);
            fields.get("fill_11").setValue(resume.getMajor()).setFont(font);
            fields.get("fill_12").setValue(resume.getExperience()).setFont(font);

            tempFileInputStream.close();
            pdf.close();


            File file = new File(newpdf);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
    try {
        PDDocument doc = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(doc);
     int pageCount = doc.getNumberOfPages();
        for(int o =0;o<pageCount;o++){
            BufferedImage image = renderer.renderImageWithDPI(o, 296);
//          BufferedImage image = renderer.renderImage(i, 2.5f);
            ImageIO.write(image, "PNG", bao);
        }
        doc.close();
    } catch (IOException e) {
        e.printStackTrace();
    }



            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(Zone.zone2());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            String ACCESS_KEY = "RMVns76oWlTpTDTSADE9F8_Lx7lmpCm6VkbgtNs-";
            String SECRET_KEY = "iph1fxlYUYktFSaI_1jnt6DgJMIKWZKcH2zSBV5h";
            String bucketname = "resume";
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = this.useIdGenerate.createid("niu");
            byte[] uploadBytes = bao.toByteArray();//.getBytes("utf-8");
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(bucketname);
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                Pdf a=new Pdf();
                a.setQiniuname("http://orzajferg.bkt.clouddn.com/"+putRet.key);
                a.setTemplate(temp);
                a.setResumeid(resumeid);
                this.pdfRepository.save(a);
//                System.out.println(putRet.key);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
            i++;
        }
    }

    public void testExportWord(String resumeid,String templeteimgurl,String template) throws Exception {

        Resume resume = this.resumeService.userresume(resumeid);

            FileInputStream tempFileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:"+templeteimgurl+".pdf"));
            String pdfname=this.useIdGenerate.createid("pdf");
            String newpdf="./pdf/"+pdfname+".pdf";
            PdfDocument pdf = new PdfDocument(new PdfReader(tempFileInputStream), new PdfWriter(newpdf));

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
            fields.get("fill_7").setValue(resume.getQualification()).setFont(font);
            fields.get("fill_8").setValue(resume.getMajor()).setFont(font);
            fields.get("fill_9").setValue(resume.getSchool()).setFont(font);
            fields.get("fill_10").setValue(resume.getOccupation()).setFont(font);
            fields.get("fill_11").setValue(resume.getMajor()).setFont(font);
            fields.get("fill_12").setValue(resume.getExperience()).setFont(font);

            tempFileInputStream.close();
            pdf.close();

            File file = new File(newpdf);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            try {
                PDDocument doc = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                for(int o =0;o<pageCount;o++){
                    BufferedImage image = renderer.renderImageWithDPI(o, 296);
//          BufferedImage image = renderer.renderImage(i, 2.5f);
                    ImageIO.write(image, "PNG", bao);
                }
                doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(Zone.zone2());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            String ACCESS_KEY = "RMVns76oWlTpTDTSADE9F8_Lx7lmpCm6VkbgtNs-";
            String SECRET_KEY = "iph1fxlYUYktFSaI_1jnt6DgJMIKWZKcH2zSBV5h";
            String bucketname = "resume";
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = this.useIdGenerate.createid("niu");
            byte[] uploadBytes = bao.toByteArray();//.getBytes("utf-8");
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(bucketname);
            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                resumeService.uptemplete(resumeid,putRet.key,template);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        }


}
