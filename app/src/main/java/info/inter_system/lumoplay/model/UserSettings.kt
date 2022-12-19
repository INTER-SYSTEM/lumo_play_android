package info.inter_system.lumoplay.model

data class UserSettings(
    var song: UInt,
    var volume: UInt,
    var lightMotive: UInt,
    var brightness: UInt,
) {
    companion object {
        val Empty = UserSettings(
            song = 0u,
            volume = 0u,
            lightMotive = 0u,
            brightness = 0u,
        )
    }
}
