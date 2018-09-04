package org.javiermf.db.statements


import org.javiermf.db.statements.StatementType.*

class StatementProcessor{

    fun process(line: String) {
        try {
            val statement = prepareStatement(line)
            executeStatement(statement)
            println("Executed!")
        } catch (e: UnrecognizedStatementException) {
            println("Unrecognized keyword at start of '$line'")
        }
    }

    private fun executeStatement(statement: Statement) {
        when (statement.type) {
            STATEMENT_INSERT -> println("This is where we would do an insert.")
            STATEMENT_SELECT -> println("This is where we would do an select.")
        }
    }

    private fun prepareStatement(line: String): Statement {
        return when (line.subSequence(0, 6)) {
            "insert" -> Statement(STATEMENT_INSERT)
            "select" -> Statement(STATEMENT_SELECT)
            else -> throw UnrecognizedStatementException()
        }
    }

}
