package app.com.parkingmanagement

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ParkingManagementApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}