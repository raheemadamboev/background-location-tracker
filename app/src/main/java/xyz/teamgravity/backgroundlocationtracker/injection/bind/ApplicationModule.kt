package xyz.teamgravity.backgroundlocationtracker.injection.bind

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.backgroundlocationtracker.core.util.AndroidLocationProvider
import xyz.teamgravity.backgroundlocationtracker.core.util.LocationProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun bindLocationProvider(androidLocationProvider: AndroidLocationProvider): LocationProvider
}