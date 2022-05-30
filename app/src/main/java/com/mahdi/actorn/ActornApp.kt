package com.mahdi.actorn

import android.app.Application
import com.mahdi.actorn.data.NetworkDataSource
import com.mahdi.actorn.repository.AppRepository

import timber.log.Timber

class ActornApp : Application()
{
     lateinit var repository : AppRepository
     override fun onCreate()
     {
          super.onCreate()
          val networkDataSource = NetworkDataSource()
          repository = AppRepository(networkDataSource)
          if (BuildConfig.DEBUG)
          {
               Timber.plant(Timber.DebugTree())
          }
     }
}