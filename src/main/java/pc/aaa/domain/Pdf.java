package pc.aaa.domain;

import com.tim.bos.BaseEntity;
import com.tim.bos.Bostype;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wu on 17-6-28.
 */
@Bostype("P05")
@Entity
@Table(name = "w_pdf")
public class Pdf extends BaseEntity implements Serializable {

    private String qiniuname;//简历在七牛云上的名称
    private String template;//简历所用的模板
    private String resumeid;//生成的简历原本所属哪条简历记录

    public String getQiniuname() {
        return qiniuname;
    }

    public void setQiniuname(String qiniuname) {
        this.qiniuname = qiniuname;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getResumeid() {
        return resumeid;
    }

    public void setResumeid(String resumeid) {
        this.resumeid = resumeid;
    }


}
