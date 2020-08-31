package com.example.entryrecording.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Entry implements Serializable {

    private static final long serialVersionUID = 3179324665681828867L;
    /**
     * 实体词条类：Entry
     *
     *e_id：词条的id
     * e_content：词条的内容（中文）
     * e_english：词条的国际英标
     * e_baseid：词条所属词条库的id
     * e_spare1：词条的备用字段1
     * e_spare2：词条的备用字段2
     *
     * Created by 梁艺翔 on 2019/6/21.
     */
    @Id
    private String e_id;
    private String e_content;
    private String e_english;
    private String e_baseid;
    private String e_spare1;
    private String e_spare2;
    @Generated(hash = 35248957)
    public Entry(String e_id, String e_content, String e_english, String e_baseid,
            String e_spare1, String e_spare2) {
        this.e_id = e_id;
        this.e_content = e_content;
        this.e_english = e_english;
        this.e_baseid = e_baseid;
        this.e_spare1 = e_spare1;
        this.e_spare2 = e_spare2;
    }
    @Generated(hash = 1759844922)
    public Entry() {
    }
    public String getE_id() {
        return this.e_id;
    }
    public void setE_id(String e_id) {
        this.e_id = e_id;
    }
    public String getE_content() {
        return this.e_content;
    }
    public void setE_content(String e_content) {
        this.e_content = e_content;
    }
    public String getE_english() {
        return this.e_english;
    }
    public void setE_english(String e_english) {
        this.e_english = e_english;
    }
    public String getE_baseid() {
        return this.e_baseid;
    }
    public void setE_baseid(String e_baseid) {
        this.e_baseid = e_baseid;
    }
    public String getE_spare1() {
        return this.e_spare1;
    }
    public void setE_spare1(String e_spare1) {
        this.e_spare1 = e_spare1;
    }
    public String getE_spare2() {
        return this.e_spare2;
    }
    public void setE_spare2(String e_spare2) {
        this.e_spare2 = e_spare2;
    }

}

