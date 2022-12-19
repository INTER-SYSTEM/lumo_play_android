package info.inter_system.lumoplay.model

data class Statistics(
    val daysInUse: UInt,
    var velocity: UInt,
    var voltage: UInt,
    var numberOfSpinsToday: UInt,
    var numberOfSpinsThisMonth: UInt,
    var numberOfSpinsTotal: UInt,
    var timeInUseToday: UInt,
    var timeInUseThisMonth: UInt,
    var timeInUseTotal: UInt,
) {
    companion object {
        val Empty = Statistics(
            daysInUse = 0u,
            velocity = 0u,
            voltage = 0u,
            numberOfSpinsToday = 0u,
            numberOfSpinsThisMonth = 0u,
            numberOfSpinsTotal = 0u,
            timeInUseToday = 0u,
            timeInUseThisMonth = 0u,
            timeInUseTotal = 0u,
        )
    }
}