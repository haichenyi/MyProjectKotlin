package com.haichenyi.myproject.model.sql;

import com.haichenyi.myproject.model.bean.User;

import java.util.List;

/**
 * Author: 海晨忆
 * Date: 2018/2/24
 * Desc:
 */
public interface SqlHelper {
  void onAdd(User user);

  void onAdd(List<User> users);

  void onDelete(String name);

  void onUpdate(String oldName,String newName);

  User onSelect(String name);

  List<User> onSelectList(String name);
}
