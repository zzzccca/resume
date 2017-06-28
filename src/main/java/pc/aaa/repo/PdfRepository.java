package pc.aaa.repo;

import org.springframework.data.repository.CrudRepository;
import pc.aaa.domain.Pdf;

import java.util.List;

/**
 * Created by wu on 17-6-28.
 */
public interface PdfRepository extends CrudRepository<Pdf,String>{

    List<Pdf> findByResumeid(String resumeid);
}
