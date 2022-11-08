package xyz.teamgravity.backgroundlocationtracker.core.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Returns true if ACCESS_COARSE_LOCATION and ACCESS_FINE_LOCATION is granted, otherwise false
 */
fun Context.checkLocationPermissions(): Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}