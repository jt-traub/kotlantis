package lemuria.ruleset

import lemuria.core.GameInterface

class GameImpl(engine: String, version: String) : GameInterface(engine, version, "standard", "1.0.0") {
    // Here we can override any of the functions to customize the game behavior such as world generation
}
