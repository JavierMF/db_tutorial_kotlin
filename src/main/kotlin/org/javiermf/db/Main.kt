package org.javiermf.db

import org.javiermf.db.meta.MetaResult
import org.javiermf.db.meta.MetaResult.META_COMMAND_EXIT
import org.javiermf.db.meta.MetaResult.META_COMMAND_UNRECOGNIZED_COMMAND
import org.javiermf.db.statements.Statement
import org.javiermf.db.statements.StatementType.STATEMENT_INSERT
import org.javiermf.db.statements.StatementType.STATEMENT_SELECT
import org.javiermf.db.statements.UnrecognizedStatementException

const val PROMPT_TEXT = "db > "
const val COMMAND_EXIT = ".exit"

fun main(args: Array<String>) {
    var mustExit = false
    var line: String

    while (!mustExit) {
        print(PROMPT_TEXT)
        line = readLine()!!.trim()

        if (isMetaCommand(line)) {
            val metaResult = doMetaCommand(line)
            when (metaResult) {
                META_COMMAND_EXIT -> mustExit = true
                META_COMMAND_UNRECOGNIZED_COMMAND -> println("Unrecognized command '$line'")
            }
        } else {
            try {
                val statement = prepareStatement(line)
                executeStatement(statement)
                println("Executed!")
            } catch (e: UnrecognizedStatementException) {
                println("Unrecognized keyword at start of '$line'")
            }
        }
    }
}

fun executeStatement(statement: Statement) {
    when (statement.type) {
        STATEMENT_INSERT -> println("This is where we would do an insert.")
        STATEMENT_SELECT -> println("This is where we would do an select.")
    }
}

fun prepareStatement(line: String): Statement {
    return when (line.subSequence(0, 6)) {
        "insert" -> Statement(STATEMENT_INSERT)
        "select" -> Statement(STATEMENT_SELECT)
        else -> throw UnrecognizedStatementException()
    }
}

fun doMetaCommand(line: String): MetaResult {
    return when (line) {
        ".exit" -> META_COMMAND_EXIT
        else -> META_COMMAND_UNRECOGNIZED_COMMAND
    }
}

fun isMetaCommand(line: String): Boolean {
    return line[0] == '.'
}

