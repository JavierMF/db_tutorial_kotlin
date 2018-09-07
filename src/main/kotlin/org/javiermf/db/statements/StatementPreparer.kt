package org.javiermf.db.statements

import org.javiermf.db.data.Row

class StatementPreparer {

    fun prepareStatement(line: String): Statement {
        val lineWords = line.split("\\s".toRegex())
        return when (lineWords[0]) {
            "insert" -> buildInsertStatement(lineWords)
            "select" -> StatementSelect()
            else -> throw UnrecognizedStatementException()
        }
    }

    private fun buildInsertStatement(lineWords: List<String>): StatementInsert {
        if (lineWords.size < 3) {
            throw PrepareSyntaxException("Not enough parameters for 'insert' statement")
        }
        return StatementInsert(Row(lineWords[1].toInt(), lineWords[2], lineWords[3]))
    }
}

class PrepareSyntaxException(message: String) : Exception(message)
class UnrecognizedStatementException : Exception("Unrecognized keyword")