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

    public List<Resume> userall(String userid){
        return this.resumeRepository.findByUserid(userid);
    }

    public Resume userresume(String id){
        return this.resumeRepository.findById(id);
    }

    public void resumedel(String id){
         this.resumeRepository.delete(id);
    }
}
