package pc.aaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.aaa.domain.User;
import pc.aaa.repo.UserRepository;

/**
 * Created by wu on 17-6-19.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User useradd(User user){
        return this.userRepository.save(user);
    }

    public User openid(String openid){
        return this.userRepository.findByOpenid(openid);
    }

}
