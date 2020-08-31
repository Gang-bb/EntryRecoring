package com.example.entryrecording.mydao;

import com.example.entryrecording.MyApplication;
import com.example.entryrecording.bean.Entry;
import com.example.entryrecording.bean.Entrybase;
import com.example.entryrecording.greendao.EntryDao;
import com.example.entryrecording.greendao.EntrybaseDao;

import java.util.List;

public class entryDao {


    public  void insertEntry(Entry entry) {
        MyApplication.getDaoInstant().getEntryDao().insertOrReplace(entry);
    }

    public  void insertentries(List<Entry> entries) {
        MyApplication.getDaoInstant().getEntryDao().insertInTx(entries);
    }
    public List<Entry> fingby(String baseid){

        List<Entry> entries=MyApplication.getDaoInstant().getEntryDao().queryBuilder().where(EntryDao.Properties.E_baseid.eq(baseid)).list();
        return  entries;
    }

    //模糊查询
    public List<Entry> fingbykey(String key){

        List<Entry> entries=MyApplication.getDaoInstant().getEntryDao().queryBuilder().where(EntryDao.Properties.E_content.like("%" + key + "%")).list();
        return  entries;
    }

    public List<Entry> findAll(){
        List<Entry> entries = MyApplication.getDaoInstant().getEntryDao().loadAll();
        return entries;
    }
    //修改
    public void detele(Entry entry){
        MyApplication.getDaoInstant().getEntryDao().update(entry);

    }




}
