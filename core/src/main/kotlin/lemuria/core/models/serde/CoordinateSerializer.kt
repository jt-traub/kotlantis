package lemuria.core.models.serde

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CoordinateSerializer : KSerializer<Pair<Int, Int>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("pair", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Pair<Int, Int>) {
        encoder.encodeString("(${value.first},${value.second})")
    }

    override fun deserialize(decoder: Decoder): Pair<Int, Int> {
        val regex = Regex("""\((-?[0-9]+),(-?[0-9]+)\)""")
        val string = decoder.decodeString()
        val res = regex.matchEntire(string)

        val foo = res?.destructured?.let { (x, y) -> Pair(x.toInt(), y.toInt()) }

        return foo ?: throw SerializationException("Invalid coordinate")
    }
}
