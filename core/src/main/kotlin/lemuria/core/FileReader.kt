package lemuria.core

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.Properties

abstract class DataReader(private val fileName: String) {
    abstract val file: InputStream

    fun stream(): InputStream {
        return file
    }

    fun name(): String {
        return fileName
    }
}

open class JsonFileReader(private val fileName: String) : DataReader(fileName) {
    override val file: InputStream
    init {
        try {
            file = File(fileName).inputStream()
        } catch (e: FileNotFoundException) {
            throw RuleDataException("Unable to load '${name()}': No such file", e)
        }
    }
}

open class JsonStringReader(private val data: String) : DataReader("<string>") {
    override val file: InputStream

    init {
        file = data.byteInputStream()
    }
}

abstract class PropertiesReader(private val fileName: String) : DataReader(fileName) {
    override val file: InputStream
    private val properties = Properties()
    init {
        file = this::class.java.classLoader.getResourceAsStream(fileName)
        properties.load(stream())
    }

    fun getProperty(key: String): String = properties.getProperty(key)
}

inline fun <reified T> loadJson(data: DataReader): T {
    try {
        return Json.decodeFromString<T>(data.stream().bufferedReader().use(BufferedReader::readText))
    } catch (e: SerializationException) {
        throw RuleDataException("Unable to load '${data.name()}': JSON error", e)
    }
}

class BuildProperties() : PropertiesReader("build.properties")
class WorldConfig() : JsonFileReader("world.json")
