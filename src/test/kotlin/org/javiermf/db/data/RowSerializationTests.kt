package org.javiermf.db.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RowSerializationTests {

    @Test
    fun shouldSerializeAndUnserializaARow() {
        val row = Row(2, "javier", "mail@gmail.com")

        val serializedRow = row.serialize()
        val deserializedRow = serializedRow.deserializeRow()

        assertThat(deserializedRow)
                .isEqualToComparingFieldByField(row)
    }
}