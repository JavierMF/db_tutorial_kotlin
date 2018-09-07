package org.javiermf.db.data

import java.nio.ByteBuffer

const val defaultPageSize = 4096

class Table(private val pageSize: Int = defaultPageSize,
            maxPages: Int = 100) {

    private val rowsPerPage = pageSize / rowSize
    private val maxRows = rowsPerPage * maxPages

    private var numberOfRows = 0
    private var pagesList: MutableList<Page> = mutableListOf()

    fun addRow(row: Row) {
        if (numberOfRows >= maxRows) {
            throw TableFullException()
        }

        val rowSlot = getRowSlot(numberOfRows)
        val pageWhereInsert = getPage(rowSlot.pageNumber)
        pageWhereInsert.insertRowAt(row.serialize(), rowSlot.offset)

        numberOfRows += 1
    }

    private fun getPage(pageNumber: Int): Page {
        if (pagesList.size - 1 < pageNumber) {
            pagesList.add(Page(pageSize))
        }
        return pagesList[pageNumber]
    }

    private fun getRowSlot(rowNumber: Int): Slot {
        val pageNum = rowNumber / rowsPerPage
        val rowOffset = rowNumber % rowsPerPage
        val byteOffset = rowOffset * rowSize
        return Slot(pageNum, byteOffset)
    }

    fun getAllRows(): List<Row> {
        val rows: MutableList<Row> = mutableListOf()
        for (rowNumber in 0..(numberOfRows - 1)) {
            val rowBytes = getRowBytes(rowNumber)
            rows.add(rowBytes.deserializeRow())
        }
        return rows
    }

    private fun getRowBytes(rowNumber: Int): ByteArray {
        val rowSlot = getRowSlot(rowNumber)
        val page = getPage(rowSlot.pageNumber)
        return page.getByteArrayFrom(rowSlot.offset)
    }


    class TableFullException : Exception("No more rows can be added to the table")

}

data class Slot(val pageNumber: Int, val offset: Int)

class Page(pageSize: Int = defaultPageSize) {

    private val byteBuffer: ByteBuffer = ByteBuffer.allocate(pageSize)

    fun insertRowAt(row: ByteArray, offset: Int) {
        byteBuffer.position(offset)
        byteBuffer.put(row)
    }

    fun getByteArrayFrom(offset: Int): ByteArray {
        val byteArray = ByteArray(rowSize)
        byteBuffer.position(offset)
        byteBuffer.get(byteArray)
        return byteArray
    }

}