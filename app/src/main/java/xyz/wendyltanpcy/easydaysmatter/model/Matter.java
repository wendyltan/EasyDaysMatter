package xyz.wendyltanpcy.easydaysmatter.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Wendy on 2017/10/21.
 */

public class Matter extends DataSupport implements Serializable{


    private String matterContent;
    private Date targetDate;
    private Date createDate;


    public Matter(){

    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public Date getTargetDate() {
        return targetDate;
    }

    public void setMatterContent(String matterContent) {
        this.matterContent = matterContent;
    }


    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getMatterContent() {
        return matterContent;
    }

}

