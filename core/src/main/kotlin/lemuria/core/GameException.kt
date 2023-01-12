package lemuria.core

// Derive all our internal game exceptions from a common root which I can trap.
open class GameException(message: String) : Exception(message)

class SaveDataException(message: String) : GameException(message)
class RuleDataException(message: String) : GameException(message)
class CommandLineException(message: String) : GameException(message)
class EditCancelledException() : GameException("edit cancelled")
