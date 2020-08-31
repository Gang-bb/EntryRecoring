package com.example.entryrecording.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Sound implements Serializable {
    private static final long serialVersionUID = 3324377415904836567L;
    /**
     * 实体录音类：Sound
     *
     * s_id:录音的id（主键）
     * s_name:录音的名字
     * s_time:录音的时长
     * s_path:录音的保存路径
     * s_createtime:录音创建的时间
     * s_filetype:录音的文件类型
     * s_entryid:录音所属词条的id（外键）
     * s_baseid:录音所属库的id（外键）
     * s_spare1备用字段1
     * s_spare2备用字段2
     *
     * Created by 梁艺翔 on 2019/6/21.
     */
    @Id
    private String s_id;
    private String s_name;
    private String s_time;
    private String s_path;
    private String s_createtime;
    private String s_filetype;
    private String s_entryid;
    private String s_baseid;
    private String s_spare1;
    private String s_spare2;
    @Generated(hash = 940087085)
    public Sound(String s_id, String s_name, String s_time, String s_path,
            String s_createtime, String s_filetype, String s_entryid,
            String s_baseid, String s_spare1, String s_spare2) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_time = s_time;
        this.s_path = s_path;
        this.s_createtime = s_createtime;
        this.s_filetype = s_filetype;
        this.s_entryid = s_entryid;
        this.s_baseid = s_baseid;
        this.s_spare1 = s_spare1;
        this.s_spare2 = s_spare2;
    }
    @Generated(hash = 127056582)
    public Sound() {
    }
    public String getS_id() {
        return this.s_id;
    }
    public void setS_id(String s_id) {
        this.s_id = s_id;
    }
    public String getS_name() {
        return this.s_name;
    }
    public void setS_name(String s_name) {
        this.s_name = s_name;
    }
    public String getS_time() {
        return this.s_time;
    }
    public void setS_time(String s_time) {
        this.s_time = s_time;
    }
    public String getS_path() {
        return this.s_path;
    }
    public void setS_path(String s_path) {
        this.s_path = s_path;
    }
    public String getS_createtime() {
        return this.s_createtime;
    }
    public void setS_createtime(String s_createtime) {
        this.s_createtime = s_createtime;
    }
    public String getS_filetype() {
        return this.s_filetype;
    }
    public void setS_filetype(String s_filetype) {
        this.s_filetype = s_filetype;
    }
    public String getS_entryid() {
        return this.s_entryid;
    }
    public void setS_entryid(String s_entryid) {
        this.s_entryid = s_entryid;
    }
    public String getS_baseid() {
        return this.s_baseid;
    }
    public void setS_baseid(String s_baseid) {
        this.s_baseid = s_baseid;
    }
    public String getS_spare1() {
        return this.s_spare1;
    }
    public void setS_spare1(String s_spare1) {
        this.s_spare1 = s_spare1;
    }
    public String getS_spare2() {
        return this.s_spare2;
    }
    public void setS_spare2(String s_spare2) {
        this.s_spare2 = s_spare2;
    }



}