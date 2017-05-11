package com.example.endless.endlesschat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.endless.endlesschat.app.Constant;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by endless .
 */

public class DataBaseManager {
    private static DataBaseManager mDataBaseManager;
    private DaoSession mDaoSession;

    public static DataBaseManager getInstant() {
        if (mDataBaseManager == null) {
            synchronized (DataBaseManager.class) {
                if (mDataBaseManager == null) {
                    mDataBaseManager = new DataBaseManager();
                }
            }
        }
        return mDataBaseManager;
    }

    public void initDatabase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, Constant.Database.DATABASE_NAME, null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();
    }


    public void savaContast(Contast contast) {
        ContastDao contastDao = mDaoSession.getContastDao();
        contastDao.save(contast);

    }

    public List<String> queryContast() {
        List<String> contast = new ArrayList<String>();
        ContastDao contastDao = mDaoSession.getContastDao();
        QueryBuilder<Contast> builder = contastDao.queryBuilder();
        List<Contast> list = builder.list();
        for (int i = 0; i <list.size(); i++) {
            contast.add(list.get(i).getUserName());
        }
        return contast;

    }
}
