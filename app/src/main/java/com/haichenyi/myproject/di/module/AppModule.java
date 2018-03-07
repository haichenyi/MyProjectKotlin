package com.haichenyi.myproject.di.module;

import com.haichenyi.myproject.base.MyApplication;
import com.haichenyi.myproject.model.DataHelper;
import com.haichenyi.myproject.model.http.HttpHelper;
import com.haichenyi.myproject.model.http.RetrofitHelper;
import com.haichenyi.myproject.model.sql.SqlHelper;
import com.haichenyi.myproject.model.sql.SqlImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
@Module
public class AppModule {
  private MyApplication application;

  public AppModule(MyApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  SqlHelper provideSqlHelper() {
    return new SqlImpl(application);
  }

  @Provides
  @Singleton
  DataHelper provideDataHelper(HttpHelper httpHelper,SqlHelper sqlHelper) {
    return new DataHelper(httpHelper,sqlHelper);
  }

  @Provides
  @Singleton
  HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
    return retrofitHelper;
  }
}
