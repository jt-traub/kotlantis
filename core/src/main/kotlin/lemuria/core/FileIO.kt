package lemuria.core

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.OutputStream
import java.util.Properties

abstract class DataReader(private val fileName: String) {
    abstract val input: InputStream

    fun input(): InputStream {
        return input
    }

    fun name(): String {
        return fileName
    }
}

abstract class DataWriter(private val fileName: String) {
    abstract val output: OutputStream

    fun output(): OutputStream {
        return output
    }

    fun name(): String {
        return fileName
    }
}

open class JsonFileReader(private val fileName: String) : DataReader(fileName) {
    override val input: InputStream
    init {
        try {
            input = File(fileName).inputStream()
        } catch (e: FileNotFoundException) {
            throw RuleDataException("Unable to load '${name()}': No such file", e)
        }
    }
}

open class JsonFileWriter(private val fileName: String) : DataWriter(fileName) {
    override val output: OutputStream
    init {
        try {
            output = File(fileName).outputStream()
        } catch (e: FileNotFoundException) {
            throw RuleDataException("Unable to write '${name()}': No such file", e)
        }
    }
}

open class JsonStringReader(private val data: String) : DataReader("<string>") {
    override val input: InputStream

    init {
        input = data.byteInputStream()
    }
}

open class JsonStringWriter() : DataWriter("<string>") {
    override val output: OutputStream

    init {
        output = ByteArrayOutputStream()
    }

    fun data(): String {
        return output.toString()
    }
} abstract class PropertiesReader(private val fileName: String) : DataReader(fileName) {
    override val input: InputStream
    private val properties = Properties()
    init {
        input = this::class.java.classLoader.getResourceAsStream(fileName)
        properties.load(input())
    }

    fun getProperty(key: String): String = properties.getProperty(key)
}

inline fun <reified T> loadJson(data: DataReader): T {
    try {
        return Json.decodeFromString<T>(data.input().bufferedReader().use(BufferedReader::readText))
    } catch (e: SerializationException) {
        throw RuleDataException("Unable to load '${data.name()}': JSON error", e)
    }
}

inline fun <reified T> saveJson(data: T, out: DataWriter) {
    try {
        out.output().write(Json { prettyPrint = true }.encodeToString(data).toByteArray())
    } catch (e: SerializationException) {
        throw RuleDataException("Unable to save '${out.name()}': JSON error", e)
    }
}

class BuildProperties() : PropertiesReader("build.properties")
class WorldConfig() : JsonFileReader("world.json")
class GameData() : JsonFileReader("game.in")

class GameOutput() : JsonFileWriter("game.out")
