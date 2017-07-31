package pc.aaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.aaa.domain.Resume;
import pc.aaa.repo.ResumeRepository;

import java.util.List;

/**
 * Created by wu on 17-6-21.
 */
@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Resume resumeadd(Resume resume){
        return this.resumeRepository.save(resume);
    }

    public Resume resumeupdate(String resumeid,String name, String tel, String gender, String hometown,
                               String birthday, String email, String major, String school,
                               String occupation, String experience, String template,String portraiturl,String templeteimgurl,String qualification,
                               String experiencetime,String interest,String introduce){
        Resume a = this.resumeRepository.findById(resumeid);
        a.setName(name);
        a.setTel(tel);
        a.setGender(gender);
        a.setHometown(hometown);
        a.setBirthday(birthday);
        a.setEmail(email);
        a.setMajor(major);
        a.setSchool(school);
        a.setOccupation(occupation);
        a.setExperience(experience);
        a.setTemplate(template);
        a.setPortraiturl(portraiturl);
        a.setTempleteimgurl(templeteimgurl);
        a.setQualification(qualification);
        a.setExperiencetime(experiencetime);
        a.setInterest(interest);
        a.setIntroduce(introduce);
        return resumeRepository.save(a);
    }

    public Resume resumeupportraiturl(String resumeid,String portraiturl){
        Resume a=this.resumeRepository.findById(resumeid);
        a.setPortraiturl(portraiturl);
        return resumeRepository.save(a);
    }

    public List<Resume> userall(String userid){
        return this.resumeRepository.findByUseridOrderByCreatetimeDesc(userid);
    }


    public Resume userresume(String id){
        return this.resumeRepository.findById(id);
    }

    public void resumedel(String id){
         this.resumeRepository.delete(id);
    }
}
