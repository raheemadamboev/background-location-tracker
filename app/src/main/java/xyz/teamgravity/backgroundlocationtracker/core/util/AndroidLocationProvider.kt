package xyz.teamgravity.backgroundlocationtracker.core.util

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.backgroundlocationtracker.core.extension.checkLocationPermissions

class AndroidLocationProvider(
    private val context: Context,
    private val manager: LocationManager,
    private val client: FusedLocationProviderClient,
) : LocationProvider {

    override fun getLocation(interval: Long): Flow<Location> {
        return callbackFlow {
            if (!context.checkLocationPermissions()) throw LocationProvider.LocationException("Missing location permissions")
            if (locationDisabled()) throw LocationProvider.LocationException("Location is disabled")

            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { launch { send(it) } }
                }
            }
            client.requestLocationUpdates(
                LocationRequest.Builder(interval).build(),
                callback,
                Looper.getMainLooper()
            )

            awaitClose {
                client.removeLocationUpdates(callback)
            }
        }
    }

    private fun locationDisabled(): Boolean {
        return !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}