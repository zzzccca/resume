package pc.aaa.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.aaa.domain.Resume;
import pc.aaa.domain.enums.ErrorCode;
import pc.aaa.service.ResumeService;

import java.util.List;

/**
 * Created by wu on 17-6-21.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class ResumeControl {

    @Autowired
    private ResumeService resumeService;

    @RequestMapping("/resume/add")
    public ErrorCode resumeadd(String userid,String name, String tel, String gender, String hometown,
                            String birthday, String email, String education, String major, String school,
                            String occupation, String coures, String experience, String template,String portraiturl,String templeteimgurl){
        Resume resume=new Resume();
        resume.setUserid(userid);
        resume.setName(name);
        resume.setTel(tel);
        resume.setGender(gender);
        resume.setHometown(hometown);
        resume.setBirthday(birthday);
        resume.setEmail(email);
        resume.setEducation(education);
        resume.setMajor(major);
        resume.setSchool(school);
        resume.setOccupation(occupation);
        resume.setCoures(coures);
        resume.setExperience(experience);
        resume.setTemplate(template);
        resume.setPortraiturl(portraiturl);
        resume.setTempleteimgurl(templeteimgurl);
        this.resumeService.resumeadd(resume);
        return ErrorCode.SUCCESS;
    }

    @RequestMapping("/resume/userall")
    public List<Resume> userall(String userid){
        return this.resumeService.userall(userid);
    }

    @RequestMapping("/resume/resumeone")
    public Resume resumeone(String resumeid){return this.resumeService.userresume(resumeid);}

    @RequestMapping("/resume/del")
    public ErrorCode resumedel(String resumeid){
        this.resumeService.resumedel(resumeid);
        return ErrorCode.SUCCESS;
    }
}
