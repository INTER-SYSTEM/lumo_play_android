package info.inter_system.lumoplay.common

import kotlin.math.roundToInt

fun Float.roundToUInt() : UInt = roundToInt().toUInt()

fun UInt.fourthUByte(): UByte = (this shr 0).toUByte()

fun UInt.thirdUByte(): UByte = (this shr 8).toUByte()

fun UInt.secondUByte(): UByte = (this shr 16).toUByte()

fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }

fun ByteArray.takeResponse(): ByteArray = drop(3).dropLast(1).toByteArray()

fun ByteArray.takeCommand(): ByteArray = dropLast(size - 3).toByteArray()

fun ByteArray.toHex() =
    joinToString("") { String.format("%02X", (it.toInt() and 0xff)) }

fun ByteArray.toUInt(): UInt {
    var result = 0u
    var shift = 0
    for (byte in this.reversed()) {

        result = result or (byte.toUByte().toUInt() shl shift)
        shift += 8
    }
    return result
}