package lemuria.core

// Derive all our internal game exceptions from a common root which I can trap.
open class GameException(message: String, cause: Throwable? = null) : Exception(message, cause)

class SaveDataException(message: String) : GameException(message)
class LoadDataException(message: String, cause: Throwable? = null) : GameException(message, cause)
class RuleDataException(message: String, cause: Throwable? = null) : GameException(message, cause)
class CommandLineException(message: String) : GameException(message)
class EditCancelledException() : GameException("edit cancelled")
