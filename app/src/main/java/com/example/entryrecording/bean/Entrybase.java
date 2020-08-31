package com.example.entryrecording.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class Entrybase implements Serializable {
    private static final long serialVersionUID = 7277104388854591521L;
    /**
     * 实体词条库类：Entrybase
     *
     *b_id ：字条库的id
     * b_name：词条库的名字
     * b_count:词条库的总条数
     * b_createtime：词条库的创建时间
     * b_spare1:词条备用字段1
     * b_spare2：词条备用字段2
     * Created by 梁艺翔 on 2019/6/21.
     */
    @Id
    private String b_id;
    private String b_b_name;
    private String b_count;
    private String b_createtime;
    private String b_spare1;
    private String b_spare2;
    @Generated(hash = 820668583)
    public Entrybase(String b_id, String b_b_name, String b_count,
            String b_createtime, String b_spare1, String b_spare2) {
        this.b_id = b_id;
        this.b_b_name = b_b_name;
        this.b_count = b_count;
        this.b_createtime = b_createtime;
        this.b_spare1 = b_spare1;
        this.b_spare2 = b_spare2;
    }
    @Generated(hash = 2007967128)
    public Entrybase() {
    }
    public String getB_id() {
        return this.b_id;
    }
    public void setB_id(String b_id) {
        this.b_id = b_id;
    }
    public String getB_b_name() {
        return this.b_b_name;
    }
    public void setB_b_name(String b_b_name) {
        this.b_b_name = b_b_name;
    }
    public String getB_count() {
        return this.b_count;
    }
    public void setB_count(String b_count) {
        this.b_count = b_count;
    }
    public String getB_createtime() {
        return this.b_createtime;
    }
    public void setB_createtime(String b_createtime) {
        this.b_createtime = b_createtime;
    }
    public String getB_spare1() {
        return this.b_spare1;
    }
    public void setB_spare1(String b_spare1) {
        this.b_spare1 = b_spare1;
    }
    public String getB_spare2() {
        return this.b_spare2;
    }
    public void setB_spare2(String b_spare2) {
        this.b_spare2 = b_spare2;
    }


}
