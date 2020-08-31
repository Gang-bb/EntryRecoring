package com.example.entryrecording.mydao;

import com.example.entryrecording.MyApplication;
import com.example.entryrecording.bean.Entry;
import com.example.entryrecording.bean.Entrybase;
import com.example.entryrecording.greendao.EntryDao;
import com.example.entryrecording.greendao.EntrybaseDao;

import java.util.List;

public class entrybaseDao {

    public  void insertbase(Entrybase base) {
        MyApplication.getDaoInstant().getEntrybaseDao().insertOrReplace(base);
    }
    public  void insertbases(List<Entrybase> bases) {
        MyApplication.getDaoInstant().getEntrybaseDao().insertInTx(bases);
    }
    public List<Entrybase> findall(){
        List<Entrybase> entrybases = MyApplication.getDaoInstant().getEntrybaseDao().loadAll();
        return entrybases;
    }


    //模糊查询
    public List<Entrybase> fingbykey(String key){

        List<Entrybase> entries=MyApplication.getDaoInstant().getEntrybaseDao().queryBuilder().where(EntrybaseDao.Properties.B_b_name.like("%" + key + "%")).list();
        return  entries;
    }

    //模糊查询
    public List<Entrybase> fingbyLive(String key){

        List<Entrybase> entries=MyApplication.getDaoInstant().getEntrybaseDao().queryBuilder().where(EntrybaseDao.Properties.B_spare1.like("%" + key + "%")).list();
        return  entries;
    }
}
