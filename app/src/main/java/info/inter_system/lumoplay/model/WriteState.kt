package info.inter_system.lumoplay.model

sealed class WriteState {
    object NoWriting : WriteState()
    class Writing(val command: Command) : WriteState()
}