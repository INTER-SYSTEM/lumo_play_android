package info.inter_system.lumoplay.model

import android.util.Log
import info.inter_system.lumoplay.common.toHex

data class BTResponse(
    val raw: ByteArray,
    val command: ByteArray = ByteArray(1),
    val value: ByteArray = ByteArray(1),
) {
    init {
        Log.e("BTResponse", "BTResponse(raw=${raw.toHex()}, command=${command.toHex()}, value=${value.toHex()})")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BTResponse

        if (!raw.contentEquals(other.raw)) return false
        if (!command.contentEquals(other.command)) return false
        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = raw.contentHashCode()
        result = 31 * result + command.contentHashCode()
        result = 31 * result + value.contentHashCode()
        return result
    }
}
