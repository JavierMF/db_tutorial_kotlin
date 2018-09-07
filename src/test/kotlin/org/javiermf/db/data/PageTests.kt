package org.javiermf.db.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PageTests {

    @Test
    fun shouldSerializeAndDeserializeARow() {
        val row = Row(2, "javier", "mail@gmail.com").serialize()
        val anOffset = 3 * rowSize

        val page = Page()
        page.insertRowAt(row, anOffset)

        assertThat(page.getByteArrayFrom(anOffset))
                .isEqualTo(row)

    }

    @Test
    fun shouldSerializeAndDeserializeSeveralRows() {
        val serializedRow = Row(2, "javier", "mail@gmail.com").serialize()
        val anOffset = 3 * rowSize

        val otherSerializedRow = Row(5, "john", "mail@yahoo.com").serialize()
        val otherOffset = 4 * rowSize

        val page = Page()
        page.insertRowAt(serializedRow, anOffset)
        page.insertRowAt(otherSerializedRow, otherOffset)

        assertThat(page.getByteArrayFrom(anOffset))
                .isEqualTo(serializedRow)
        assertThat(page.getByteArrayFrom(otherOffset))
                .isEqualTo(otherSerializedRow)

    }
}