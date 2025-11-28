package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import models.Game
import models.PlaySession
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * A JSON-based serializer that reads and writes objects to a file
 * using the XStream library with the JettisonMappedXmlDriver.
 *
 * Supports serializing and deserializing [Game] and [PlaySession] objects.
 *
 * @property file the file to read from or write to
 */
class JSONSerializer(private val file: File) : Serializer {
    /**
     * Reads and deserializes objects from the JSON file.
     *
     * @return the deserialized object (typically a list of [Game] or [PlaySession])
     * @throws Exception if reading or deserialization fails
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Game::class.java, PlaySession::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Serializes and writes the given object to the JSON file.
     *
     * @param obj the object to serialize (typically a list of [Game] or [PlaySession])
     * @throws Exception if writing or serialization fails
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
