package pc.aaa.control;

import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.aaa.service.UseIdGenerate;

/**
 * Created by wu on 17-7-6.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class QiniuControl {

    @Autowired
    private UseIdGenerate useIdGenerate;

    String ACCESS_KEY = "RMVns76oWlTpTDTSADE9F8_Lx7lmpCm6VkbgtNs-";
    String SECRET_KEY = "iph1fxlYUYktFSaI_1jnt6DgJMIKWZKcH2zSBV5h";
    String bucketname = "resume";

    Auth auth=Auth.create(ACCESS_KEY,SECRET_KEY);

    @RequestMapping("qiniu/uptoken")
    public String getUpToken(){
        return "{\"uptoken\":\"" + auth.uploadToken(bucketname) + "\",\"key\":\"" + useIdGenerate.createid("I05") + "\"}";
    }

}
