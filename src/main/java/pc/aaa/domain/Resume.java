package pc.aaa.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-6-19.
 */
@Bostype("R05")
@Entity
@Table(name = "w_resume")
public class Resume extends BaseEntity implements Serializable{

    private String userid;//用户id
    private String name;//姓名
    private String tel;//电话
    private String gender;//性别
    private String hometown;//籍贯
    private String birthday;//出生日期
    private String email;//邮箱
    private String education;//学历
    private String major;//专业
    private String school;//毕业院校
    private String occupation;//求职意向
    private String coures;//专业课程
    private String experience;//工作经历
    private String template;//简历模板
    private String portraiturl;//头像
    private String templeteimgurl;//模板图片
    private String qualification;//学历
    private String experiencetime;//工作经验
    private String interest;//兴趣爱好
    private String introduce;//个人评价
    private String qiniuname;//简历在七牛云上的key

    public String getQiniuname() {
        return qiniuname;
    }

    public void setQiniuname(String qiniuname) {
        this.qiniuname = qiniuname;
    }


    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperiencetime() {
        return experiencetime;
    }

    public void setExperiencetime(String experiencetime) {
        this.experiencetime = experiencetime;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    public String getPortraiturl() {
        return portraiturl;
    }

    public void setPortraiturl(String portraiturl) {
        this.portraiturl = portraiturl;
    }


    public String getTempleteimgurl() {
        return templeteimgurl;
    }

    public void setTempleteimgurl(String templeteimgurl) {
        this.templeteimgurl = templeteimgurl;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCoures() {
        return coures;
    }

    public void setCoures(String coures) {
        this.coures = coures;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
