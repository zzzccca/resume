package pc.aaa.control;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pc.aaa.domain.User;
import pc.aaa.domain.enums.ErrorCode;
import pc.aaa.service.UserService;
import pc.aaa.service.WeixinidService;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wu on 17-6-19.
 */
@RestController
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class UserControl {

    @Autowired
    private UserService userService;

    @Autowired
    private WeixinidService weixinidService;

    @RequestMapping(value = "/wx/login", method = RequestMethod.GET)
    public Object useradd(String encryptedData, String iv, String code){
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return ErrorCode.CONTENTNULL;
        }

        String a =(String) this.weixinidService.decodeUserInfo(encryptedData,iv,code);
        JSONObject userInfoJSON = JSONObject.fromObject(a);

        User u=this.userService.openid(userInfoJSON.get("openId").toString());
        if (u!=null){
            Map map =new HashMap();
            map.put("errorcode",'0');
            map.put("errorinfo",u.getId());
            return map;
        }else {
            User user = new User();
            user.setOpenid(userInfoJSON.get("openId").toString());
            user.setNickname(userInfoJSON.get("nickName").toString());
            user.setAvatarurl(userInfoJSON.get("avatarUrl").toString());
            user.setGender(userInfoJSON.get("gender").toString());
            user.setProvince(userInfoJSON.get("province").toString());
            user.setCity(userInfoJSON.get("city").toString());
            user.setCountry(userInfoJSON.get("country").toString());
            if (userInfoJSON.get("unionid") != null) {
                user.setUnionid(userInfoJSON.get("unionId").toString());
            }
            this.userService.useradd(user);
        }
        Map map =new HashMap();
        map.put("errorcode",'0');
        map.put("errorinfo",u.getId());
        return map;
    }
}
