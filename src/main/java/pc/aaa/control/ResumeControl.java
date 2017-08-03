package pc.aaa.control;


import org.bouncycastle.jcajce.provider.symmetric.IDEA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.aaa.domain.Resume;
import pc.aaa.domain.enums.ErrorCode;
import pc.aaa.service.PdfService;
import pc.aaa.service.ResumeService;

import java.util.List;

/**
 * Created by wu on 17-6-21.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class ResumeControl {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ResumeService resumeService;

    @RequestMapping("/resume/add")
    public ErrorCode resumeadd(String userid,String name, String tel, String gender, String hometown,
                            String birthday, String email, String major, String school,
                            String occupation, String experience, String template,String portraiturl,String templeteimgurl,String qualification,
                               String experiencetime,String interest,String introduce){
        Resume resume=new Resume();
        resume.setUserid(userid);
        resume.setName(name);
        resume.setTel(tel);
        resume.setGender(gender);
        resume.setHometown(hometown);
        resume.setBirthday(birthday);
        resume.setEmail(email);
        resume.setMajor(major);
        resume.setSchool(school);
        resume.setOccupation(occupation);
        resume.setExperience(experience);
        resume.setTemplate(template);
        resume.setTempleteimgurl(templeteimgurl);
        resume.setPortraiturl(portraiturl);
        resume.setQualification(qualification);
        resume.setExperiencetime(experiencetime);
        resume.setInterest(interest);
        resume.setIntroduce(introduce);
        String resumeid=this.resumeService.resumeadd(resume).getId();
        try {
            pdfService.testExportWord2(resumeid);
            pdfService.testExportWord(resumeid,template);
        }catch (Exception e){
            System.err.println(e);
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/resume/update")
    public ErrorCode resumeupdate(String resumeid,String name, String tel, String gender, String hometown,
                               String birthday, String email, String major, String school,
                               String occupation, String experience, String template,String portraiturl,String templeteimgurl,String qualification,
                               String experiencetime,String interest,String introduce){

        this.resumeService.resumeupdate(resumeid,name,tel, gender, hometown,birthday, email, major, school,
                occupation, experience, template,portraiturl,templeteimgurl,qualification, experiencetime,interest,introduce);
        return ErrorCode.SUCCESS;
    }
    @RequestMapping("resume/uptemplate")
    public ErrorCode resumeuptemplate(String resumeid,String template){
        try {
            pdfService.testExportWord(resumeid,template);
        }catch (Exception e){
            System.err.println(e);
        }
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("resume/upportraiturl")
    public ErrorCode resumeupportraiturl(String resumeid,String portraiturl){
        this.resumeService.resumeupportraiturl(resumeid,portraiturl);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/resume/userall")
    public List<Resume> userall(String userid){
        if (StringUtils.hasText(userid)){
        return this.resumeService.userall(userid);
        }else
            return null;
    }

    @RequestMapping("/resume/all")
    public Object resumeall(int page,int row){
        return this.resumeService.resumeall(page,row);
    }

    @RequestMapping("/resume/resumeone")
    public Resume resumeone(String resumeid){
        return this.resumeService.userresume(resumeid);
    }

    @RequestMapping("/resume/del")
    public ErrorCode resumedel(String resumeid){
        this.resumeService.resumedel(resumeid);
        return ErrorCode.SUCCESS;
    }
}
