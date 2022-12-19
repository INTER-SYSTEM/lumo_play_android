package info.inter_system.lumoplay

import android.app.Application
import info.inter_system.lumoplay.common.BluetoothService
import info.inter_system.lumoplay.common.NavigationService

class CarouselApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        BluetoothService.create(this)
        NavigationService.create()
    }

    companion object {
        lateinit var instance: CarouselApp
    }
}
