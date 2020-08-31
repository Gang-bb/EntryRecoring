package com.example.entryrecording.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.entryrecording.bean.Entrybase;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ENTRYBASE".
*/
public class EntrybaseDao extends AbstractDao<Entrybase, String> {

    public static final String TABLENAME = "ENTRYBASE";

    /**
     * Properties of entity Entrybase.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property B_id = new Property(0, String.class, "b_id", true, "B_ID");
        public final static Property B_b_name = new Property(1, String.class, "b_b_name", false, "B_B_NAME");
        public final static Property B_count = new Property(2, String.class, "b_count", false, "B_COUNT");
        public final static Property B_createtime = new Property(3, String.class, "b_createtime", false, "B_CREATETIME");
        public final static Property B_spare1 = new Property(4, String.class, "b_spare1", false, "B_SPARE1");
        public final static Property B_spare2 = new Property(5, String.class, "b_spare2", false, "B_SPARE2");
    }


    public EntrybaseDao(DaoConfig config) {
        super(config);
    }
    
    public EntrybaseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ENTRYBASE\" (" + //
                "\"B_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: b_id
                "\"B_B_NAME\" TEXT," + // 1: b_b_name
                "\"B_COUNT\" TEXT," + // 2: b_count
                "\"B_CREATETIME\" TEXT," + // 3: b_createtime
                "\"B_SPARE1\" TEXT," + // 4: b_spare1
                "\"B_SPARE2\" TEXT);"); // 5: b_spare2
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ENTRYBASE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Entrybase entity) {
        stmt.clearBindings();
 
        String b_id = entity.getB_id();
        if (b_id != null) {
            stmt.bindString(1, b_id);
        }
 
        String b_b_name = entity.getB_b_name();
        if (b_b_name != null) {
            stmt.bindString(2, b_b_name);
        }
 
        String b_count = entity.getB_count();
        if (b_count != null) {
            stmt.bindString(3, b_count);
        }
 
        String b_createtime = entity.getB_createtime();
        if (b_createtime != null) {
            stmt.bindString(4, b_createtime);
        }
 
        String b_spare1 = entity.getB_spare1();
        if (b_spare1 != null) {
            stmt.bindString(5, b_spare1);
        }
 
        String b_spare2 = entity.getB_spare2();
        if (b_spare2 != null) {
            stmt.bindString(6, b_spare2);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Entrybase entity) {
        stmt.clearBindings();
 
        String b_id = entity.getB_id();
        if (b_id != null) {
            stmt.bindString(1, b_id);
        }
 
        String b_b_name = entity.getB_b_name();
        if (b_b_name != null) {
            stmt.bindString(2, b_b_name);
        }
 
        String b_count = entity.getB_count();
        if (b_count != null) {
            stmt.bindString(3, b_count);
        }
 
        String b_createtime = entity.getB_createtime();
        if (b_createtime != null) {
            stmt.bindString(4, b_createtime);
        }
 
        String b_spare1 = entity.getB_spare1();
        if (b_spare1 != null) {
            stmt.bindString(5, b_spare1);
        }
 
        String b_spare2 = entity.getB_spare2();
        if (b_spare2 != null) {
            stmt.bindString(6, b_spare2);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Entrybase readEntity(Cursor cursor, int offset) {
        Entrybase entity = new Entrybase( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // b_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // b_b_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // b_count
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // b_createtime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // b_spare1
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // b_spare2
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Entrybase entity, int offset) {
        entity.setB_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setB_b_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setB_count(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setB_createtime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setB_spare1(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setB_spare2(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Entrybase entity, long rowId) {
        return entity.getB_id();
    }
    
    @Override
    public String getKey(Entrybase entity) {
        if(entity != null) {
            return entity.getB_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Entrybase entity) {
        return entity.getB_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
