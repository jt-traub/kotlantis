

package lemuria.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class GameConfigTest {
    val conf = """
    {
        "ruleset": "test",
        "version": "0.0.0",
        "world": "MyTest",
        "flags": [],
        "setup": [
            { "name": "layer1", "type": "type1" },
            { "name": "layer2", "type": "type2" },
            { "name": "layer3", "type": "type3" }
        ]
    }
    """

    @Test
    fun createWorld() {
        val gamedef = GameConfig(JsonStringReader(conf))
        assertNull(gamedef.data)
        gamedef.createWorld()
        assertNotNull(gamedef.data)
        assertEquals(3, gamedef.data!!.layers.size)
        assertEquals("layer2", gamedef.data!!.layers[1]?.name)
        assertEquals(4, gamedef.data!!.units.size)
        assertEquals("test2", gamedef.data!!.units[2].name)
    }

    @Test
    fun saveData() {
        val str =
"""{
    "layers": {
        "0": {
            "name": "layer1",
            "type": "type1",
            "regions": {
                "(0,0)": {
                    "name": "test",
                    "coord": "(0,0)"
                }
            }
        },
        "1": {
            "name": "layer2",
            "type": "type2",
            "regions": {
                "(1,1)": {
                    "name": "test",
                    "coord": "(1,1)"
                }
            }
        },
        "2": {
            "name": "layer3",
            "type": "type3",
            "regions": {
                "(2,2)": {
                    "name": "test",
                    "coord": "(2,2)"
                }
            }
        }
    },
    "units": [
        {
            "id": 0,
            "name": "test0"
        },
        {
            "id": 1,
            "name": "test1"
        },
        {
            "id": 2,
            "name": "test2"
        },
        {
            "id": 3,
            "name": "test3"
        }
    ]
}"""
        val gamedef = GameConfig(JsonStringReader(conf))
        assertNull(gamedef.data)
        gamedef.createWorld()
        assertNotNull(gamedef.data)
        val output = JsonStringWriter()
        gamedef.saveData(output)
        assertEquals(str, output.data())
    }
}
