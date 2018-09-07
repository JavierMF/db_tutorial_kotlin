package org.javiermf.db.data

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableTests {

    @Test
    fun shouldAddAndRetrieveARow() {
        val table = Table()

        val row = Row(2, "javier", "mail@gmail.com")
        table.addRow(row)
        val allRows = table.getAllRows()

        assertThat(allRows).hasSize(1)

        assertThat(allRows[0])
                .isEqualToComparingFieldByField(row)
    }

    @Test
    fun shouldUseDifferentPages() {
        val smallTable = Table(pageSize = rowSize, maxPages = 2)

        val row = Row(2, "javier", "mail@gmail.com")
        smallTable.addRow(row)

        val otherRow = Row(5, "john", "mail@yahoo.com")
        smallTable.addRow(otherRow)

        assertThat(smallTable.getAllRows()).hasSize(2)

    }

    @Test
    fun shouldBanExcesiveRows() {
        val smallTable = Table(pageSize = rowSize, maxPages = 1)

        val row = Row(2, "javier", "mail@gmail.com")
        smallTable.addRow(row)

        val otherRow = Row(5, "john", "mail@yahoo.com")

        assertThatExceptionOfType(Table.TableFullException::class.java)
                .isThrownBy { smallTable.addRow(otherRow) }

    }

}