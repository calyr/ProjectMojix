package app.calyr.com.projectmojix

import android.app.Application
import android.arch.persistence.room.Room

val db: MyDataBase by lazy {
    App.INSTANCE!!
}

class App : Application() {
    companion object {
        val NAME = "mojix.db"
        var INSTANCE: MyDataBase? = null
    }
    override fun onCreate() {
        INSTANCE = Room.databaseBuilder(applicationContext, MyDataBase::class.java, NAME)
                .allowMainThreadQueries()
                .build()
        super.onCreate()
    }
}

