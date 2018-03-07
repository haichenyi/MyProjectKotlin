package com.haichenyi.myproject.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: 海晨忆
 * Date: 2018/2/24
 * Desc: 用户表的bean类
 */
@Entity
public class User {
  @Id
  private Long id;
  private String name;
  private int sex;
  @Property(nameInDb = "Height")
  private int height;
  private String weight;
  @NotNull
  private int age;
  @Transient
  private String character;

  @Generated(hash = 719868967)
  public User(Long id, String name, int sex, int height, String weight, int age) {
      this.id = id;
      this.name = name;
      this.sex = sex;
      this.height = height;
      this.weight = weight;
      this.age = age;
  }

  @Generated(hash = 586692638)
  public User() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSex() {
    return this.sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getWeight() {
    return this.weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }
}
