package org.javiermf.db.statements

import org.javiermf.db.data.Row
import org.javiermf.db.data.emailSize
import org.javiermf.db.data.userNameSize

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
        val idValue = parsePositiveIntValue("id", lineWords[1])
        val userNameValue = parseStringValue("userName", lineWords[2], userNameSize)
        val emailValue = parseStringValue("email", lineWords[3], emailSize)

        val row = Row(idValue, userNameValue, emailValue)
        return StatementInsert(row)
    }

    private fun parsePositiveIntValue(paramName: String, paramValue: String): Int {
        if (paramValue.toIntOrNull() == null) {
            throw PrepareSyntaxException("$paramName parameter is not an int: $paramValue")
        }
        val intValue = paramValue.toInt()
        if (intValue < 0) {
            throw PrepareSyntaxException("$paramName parameter must be positive: $paramValue")
        }

        return intValue
    }

    private fun parseStringValue(paramName: String, paramValue: String, maxSize: Int): String {
        if (paramValue.length > maxSize) {
            throw PrepareSyntaxException("$paramName parameter $paramValue is too long. Max length $maxSize")
        }
        return paramValue
    }
}

class PrepareSyntaxException(message: String) : Exception(message)
class UnrecognizedStatementException : Exception("Unrecognized keyword")