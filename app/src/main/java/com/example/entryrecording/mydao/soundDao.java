package com.example.entryrecording.mydao;

import com.example.entryrecording.MyApplication;
import com.example.entryrecording.bean.Entry;
import com.example.entryrecording.bean.Sound;
import com.example.entryrecording.greendao.EntryDao;
import com.example.entryrecording.greendao.SoundDao;

import java.util.List;

public class soundDao {
    public  void insertSound(Sound sound) {
        MyApplication.getDaoInstant().getSoundDao().insertOrReplace(sound);
    }

    public  void insertSounds(List<Sound> sounds) {
        MyApplication.getDaoInstant().getSoundDao().insertInTx(sounds);
    }
    public List<Sound> findby(String entryid){

        List<Sound> entries=MyApplication.getDaoInstant().getSoundDao().queryBuilder().where(SoundDao.Properties.S_entryid.eq(entryid)).list();
        return  entries;
    }
    public Sound findbyid(String id){
        Sound sound = MyApplication.getDaoInstant().getSoundDao().load(id);
        return sound;
    }

}
