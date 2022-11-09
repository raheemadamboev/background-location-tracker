package xyz.teamgravity.backgroundlocationtracker.core.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.backgroundlocationtracker.core.notification.LocationNotification
import xyz.teamgravity.backgroundlocationtracker.core.util.LocationProvider
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : LifecycleService() {

    companion object {
        const val ACTION_START = "actionStart"
        const val ACTION_STOP = "actionStop"
        const val INTERVAL = 10_000L
    }

    @Inject
    lateinit var notification: LocationNotification

    @Inject
    lateinit var location: LocationProvider

    private var locationJob: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        observe()
        startForeground()
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun observe() {
        observeLocation()
    }

    private fun startForeground() {
        startForeground(LocationNotification.ID, notification.createNotification())
    }

    private fun observeLocation() {
        locationJob?.cancel()
        locationJob = lifecycleScope.launch {
            location.getLocation(INTERVAL)
                .catch { it.printStackTrace() }
                .collectLatest { location ->
                    notification.updateContentText(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
        }
    }
}