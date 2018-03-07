package com.haichenyi.myproject.model.sql;

import com.haichenyi.myproject.base.MyApplication;
import com.haichenyi.myproject.model.bean.DaoMaster;
import com.haichenyi.myproject.model.bean.DaoSession;
import com.haichenyi.myproject.model.bean.User;
import com.haichenyi.myproject.model.bean.UserDao;
import com.haichenyi.myproject.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 海晨忆
 * Date: 2018/2/24
 * Desc:
 */
public class SqlImpl implements SqlHelper {
  private final UserDao userDao;

  @Override
  public void onAdd(User user) {
    userDao.insert(user);
    ToastUtils.Companion.showTipMsg("添加成功");
  }

  @Override
  public void onAdd(List<User> users) {
    userDao.insertInTx(users);
    ToastUtils.Companion.showTipMsg("添加成功");
  }

  /**
   * 初始化Sql Dao.
   *
   * @param application {@link MyApplication}
   */
  public SqlImpl(MyApplication application) {
    SqlOpenHelper helper = new SqlOpenHelper(application, "haichenyi.db");
    DaoSession daoSession = new DaoMaster(helper.getWritableDb()).newSession();
    userDao = daoSession.getUserDao();
  }


  @Override
  public void onDelete(String name) {
    /*//通过主键删除一个对象，删除满足条件的第一个对象
    User user1 = userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().unique();
    //一定要记得做非空判断
    if (user1 != null) {
      userDao.deleteByKey(user1.getId());
      ToastUtils.Companion.showTipMsg("删除成功");
    } else {
      ToastUtils.Companion.showTipMsg("未查询到相关数据");
    }*/

    //通过主键删除一个满足条件的List
    List<User> list = userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().list();
    if (list.size() > 0) {
      List<Long> keys = new ArrayList<>();
      for (User user : list) {
        keys.add(user.getId());
      }
      userDao.deleteByKeyInTx(keys);
      ToastUtils.Companion.showTipMsg("删除成功");
    } else {
      ToastUtils.Companion.showTipMsg("未查询到相关数据");
    }
  }

  @Override
  public void onUpdate(String oldName, String newName) {
    List<User> list = userDao.queryBuilder().where(UserDao.Properties.Name.eq(oldName)).build().list();
    if (list.size() > 0) {
      for (User user : list) {
        user.setName(newName);
      }
//      userDao.update(user1);
      userDao.updateInTx(list);
      ToastUtils.Companion.showTipMsg("修改成功");
    } else {
      ToastUtils.Companion.showTipMsg("未查询到相关数据");
    }
  }

  @Override
  public User onSelect(String name) {
    return userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().unique();
  }

  @Override
  public List<User> onSelectList(String name) {
    return userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().list();
  }

}
