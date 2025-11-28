package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Game
import models.PlaySession
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import kotlin.Throws

/**
 * A serializer implementation that persists objects to an XML file
 * and reads them back using XStream with a DOM driver.
 *
 * Supports serialization of [Game] and [PlaySession] objects.
 *
 * @property file the XML file to read/write objects
 */
class XMLSerializer(private val file: File) : Serializer {
    /**
     * Reads and deserializes objects from the XML file.
     *
     * @return the deserialized object(s)
     * @throws Exception if reading from file or deserialization fails
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Game::class.java, PlaySession::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Serializes the given object(s) and writes them to the XML file.
     *
     * @param obj the object(s) to serialize; may be `null`
     * @throws Exception if writing to file or serialization fails
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
