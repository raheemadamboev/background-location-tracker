package xyz.teamgravity.backgroundlocationtracker.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import xyz.teamgravity.backgroundlocationtracker.R

class LocationNotification(
    private val context: Context,
    private val manager: NotificationManager,
) {

    companion object {
        private const val CHANNEL_ID = "location_channel"
        const val ID = 1
    }

    private val builder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.tracking_location))
            .setContentText(context.getString(R.string.your_location, "-", "-"))
            .setSmallIcon(R.drawable.ic_location)
            .setOngoing(true)
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID, context.getString(R.string.location_tracker), NotificationManager.IMPORTANCE_LOW).apply {
                description = context.getString(R.string.location_tracker_description)
                manager.createNotificationChannel(this)
            }
        }
    }

    fun createNotification(): Notification {
        return builder.build()
    }

    fun updateContentText(latitude: Double, longitude: Double) {
        val notification =
            builder.setContentText(context.getString(R.string.your_location, latitude.toString(), longitude.toString())).build()
        manager.notify(ID, notification)
    }
}