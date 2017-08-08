package pc.aaa.service;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pc.aaa.domain.Resume;
import pc.aaa.domain.enums.ErrorCode;
import pc.aaa.repo.ResumeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Object resumeall(int page,int row,String name){
        Pageable pageable=new PageRequest(page-1,row);
        Page<Resume> list=this.resumeRepository.findAll(pageable);
        try {
            if (StringUtils.hasText(name)){
                list=this.resumeRepository.findByNameLike(pageable,name);
            }
        if (page>list.getTotalPages()){
            return ErrorCode.Lastpage;
        }else {
            Map map=new HashMap();
            map.put("total",list.getTotalElements());//数据总数
            map.put("totalpage",list.getTotalPages());//总页数
            map.put("rows",list.getContent());//分页应该显示的数据
            return map;
             }
        }catch (IllegalArgumentException e){
            return ErrorCode.Firstpage;
        }
    }


    public Resume userresume(String id){
        return this.resumeRepository.findById(id);
    }

    public void resumedel(String id){
         this.resumeRepository.delete(id);
    }

    public void uptemplete(String resumeid,String templeteimgurl,String template){
        Resume a = resumeRepository.findById(resumeid);
        a.setTemplate(template);
        a.setTempleteimgurl(templeteimgurl);
//        a.setTempleteimgurl("http://orzajferg.bkt.clouddn.com/"+templeteimgurl);
        resumeRepository.save(a);
    }
}
