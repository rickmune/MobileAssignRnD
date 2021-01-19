package com.rickmune.mobileassignrnd

import android.app.Application
import com.rickmune.mobileassignrnd.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AssignRndApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AssignRndApplication)
            modules(
                searchModule
            )
        }
    }
}
