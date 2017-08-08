package pc.aaa.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.aaa.domain.Resume;

import java.util.List;

/**
 * Created by wu on 17-6-21.
 */
@Repository
public interface ResumeRepository extends CrudRepository<Resume,String>{

    List<Resume> findByUseridOrderByCreatetimeDesc(String userid);

    Resume findById(String id);

    Page<Resume> findAll(Pageable pageable);

    Page<Resume> findByNameLike(Pageable pageable,String name);
}
