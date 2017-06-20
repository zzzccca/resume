package pc.aaa.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pc.aaa.domain.User;

/**
 * Created by wu on 17-6-19.
 */
@Repository
public interface UserRepository extends CrudRepository<User,String>{

    User findByOpenid(String openid);
}
