package xyz.teamgravity.backgroundlocationtracker.injection.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import xyz.teamgravity.backgroundlocationtracker.core.notification.LocationNotification
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var notification: LocationNotification

    override fun onCreate() {
        super.onCreate()

        notification.createChannel()
    }
}