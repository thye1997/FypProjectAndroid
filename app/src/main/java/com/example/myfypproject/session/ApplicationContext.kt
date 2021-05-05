package com.example.myfypproject.session

import android.app.Application
import android.content.Context

public class ApplicationContext : Application(){

    companion object {
        private lateinit var mContext: Context

        fun getApplicationContext(): Context {
            return mContext
        }
        fun setApplicationContext(con: Context){
            mContext = con
        }
    }

}