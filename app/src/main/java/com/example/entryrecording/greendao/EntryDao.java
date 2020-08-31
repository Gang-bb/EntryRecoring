package com.example.entryrecording.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.entryrecording.bean.Entry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ENTRY".
*/
public class EntryDao extends AbstractDao<Entry, String> {

    public static final String TABLENAME = "ENTRY";

    /**
     * Properties of entity Entry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property E_id = new Property(0, String.class, "e_id", true, "E_ID");
        public final static Property E_content = new Property(1, String.class, "e_content", false, "E_CONTENT");
        public final static Property E_english = new Property(2, String.class, "e_english", false, "E_ENGLISH");
        public final static Property E_baseid = new Property(3, String.class, "e_baseid", false, "E_BASEID");
        public final static Property E_spare1 = new Property(4, String.class, "e_spare1", false, "E_SPARE1");
        public final static Property E_spare2 = new Property(5, String.class, "e_spare2", false, "E_SPARE2");
    }


    public EntryDao(DaoConfig config) {
        super(config);
    }
    
    public EntryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ENTRY\" (" + //
                "\"E_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: e_id
                "\"E_CONTENT\" TEXT," + // 1: e_content
                "\"E_ENGLISH\" TEXT," + // 2: e_english
                "\"E_BASEID\" TEXT," + // 3: e_baseid
                "\"E_SPARE1\" TEXT," + // 4: e_spare1
                "\"E_SPARE2\" TEXT);"); // 5: e_spare2
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ENTRY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Entry entity) {
        stmt.clearBindings();
 
        String e_id = entity.getE_id();
        if (e_id != null) {
            stmt.bindString(1, e_id);
        }
 
        String e_content = entity.getE_content();
        if (e_content != null) {
            stmt.bindString(2, e_content);
        }
 
        String e_english = entity.getE_english();
        if (e_english != null) {
            stmt.bindString(3, e_english);
        }
 
        String e_baseid = entity.getE_baseid();
        if (e_baseid != null) {
            stmt.bindString(4, e_baseid);
        }
 
        String e_spare1 = entity.getE_spare1();
        if (e_spare1 != null) {
            stmt.bindString(5, e_spare1);
        }
 
        String e_spare2 = entity.getE_spare2();
        if (e_spare2 != null) {
            stmt.bindString(6, e_spare2);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Entry entity) {
        stmt.clearBindings();
 
        String e_id = entity.getE_id();
        if (e_id != null) {
            stmt.bindString(1, e_id);
        }
 
        String e_content = entity.getE_content();
        if (e_content != null) {
            stmt.bindString(2, e_content);
        }
 
        String e_english = entity.getE_english();
        if (e_english != null) {
            stmt.bindString(3, e_english);
        }
 
        String e_baseid = entity.getE_baseid();
        if (e_baseid != null) {
            stmt.bindString(4, e_baseid);
        }
 
        String e_spare1 = entity.getE_spare1();
        if (e_spare1 != null) {
            stmt.bindString(5, e_spare1);
        }
 
        String e_spare2 = entity.getE_spare2();
        if (e_spare2 != null) {
            stmt.bindString(6, e_spare2);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Entry readEntity(Cursor cursor, int offset) {
        Entry entity = new Entry( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // e_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // e_content
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // e_english
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // e_baseid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // e_spare1
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // e_spare2
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Entry entity, int offset) {
        entity.setE_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setE_content(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setE_english(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setE_baseid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setE_spare1(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setE_spare2(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Entry entity, long rowId) {
        return entity.getE_id();
    }
    
    @Override
    public String getKey(Entry entity) {
        if(entity != null) {
            return entity.getE_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Entry entity) {
        return entity.getE_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}