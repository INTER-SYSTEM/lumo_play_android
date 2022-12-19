package info.inter_system.lumoplay.model

data class AdminSettings(
    var daySong: UInt,
    var nightSong: UInt,
    var dayVolume: UInt,
    var nightVolume: UInt,
    var lightMotive: UInt,
    var dayBrightness: UInt,
    var nightBrightness: UInt,
    var time: UInt,
    var date: UInt,
    var nightTimeStart: UInt,
    var nightTimeEnd: UInt,
    var jingle: UInt,
) {
    companion object {
        val Empty = AdminSettings(
            daySong = 0u,
            nightSong = 0u,
            dayVolume = 0u,
            nightVolume = 0u,
            lightMotive = 0u,
            dayBrightness = 0u,
            nightBrightness = 0u,
            time = 0u,
            date = 0u,
            nightTimeStart = 0u,
            nightTimeEnd = 0u,
            jingle = 0u,
        )
    }
}
