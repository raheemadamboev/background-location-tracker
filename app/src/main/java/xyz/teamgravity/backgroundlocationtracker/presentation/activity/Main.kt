package xyz.teamgravity.backgroundlocationtracker.presentation.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.backgroundlocationtracker.R
import xyz.teamgravity.backgroundlocationtracker.core.service.LocationService
import xyz.teamgravity.backgroundlocationtracker.presentation.theme.BackgroundLocationTrackerTheme

@AndroidEntryPoint
class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            0
        )
        setContent {
            BackgroundLocationTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(
                            onClick = {
                                Intent(context, LocationService::class.java).apply {
                                    action = LocationService.ACTION_START
                                    startService(this)
                                }
                            }
                        ) {
                            Text(text = stringResource(id = R.string.start))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                Intent(context, LocationService::class.java).apply {
                                    action = LocationService.ACTION_STOP
                                    startService(this)
                                }
                            }
                        ) {
                            Text(text = stringResource(id = R.string.stop))
                        }
                    }
                }
            }
        }
    }
}