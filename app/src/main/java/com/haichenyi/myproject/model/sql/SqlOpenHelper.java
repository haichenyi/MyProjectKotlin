package com.haichenyi.myproject.model.sql;

import android.content.Context;

import com.haichenyi.myproject.model.bean.DaoMaster;
import com.haichenyi.myproject.model.bean.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Author: 海晨忆
 * Date: 2018/2/24
 * Desc:
 */
public class SqlOpenHelper extends DaoMaster.OpenHelper {
  public SqlOpenHelper(Context context, String name) {
    super(context, name);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onUpgrade(Database db, int oldVersion, int newVersion) {
    super.onUpgrade(db, oldVersion, newVersion);

    MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
      @Override
      public void onCreateAllTables(Database db, boolean ifNotExists) {
        DaoMaster.createAllTables(db, ifNotExists);
      }

      @Override
      public void onDropAllTables(Database db, boolean ifExists) {
        DaoMaster.dropAllTables(db, ifExists);
      }
    }, UserDao.class);
  }
}
