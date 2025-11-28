package persistence

/**
 * A generic interface for serializing and deserializing objects
 * to and from a persistent storage format (e.g., JSON, XML).
 */
interface Serializer {
    /**
     * Serializes the given object and writes it to the underlying storage.
     *
     * @param obj the object to serialize; may be `null`
     * @throws Exception if writing or serialization fails
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads and deserializes an object from the underlying storage.
     *
     * @return the deserialized object, or `null` if no data exists
     * @throws Exception if reading or deserialization fails
     */
    @Throws(Exception::class)
    fun read(): Any?
}
