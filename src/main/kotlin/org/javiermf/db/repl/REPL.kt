package org.javiermf.db.repl

import org.javiermf.db.PROMPT_TEXT
import org.javiermf.db.meta.MetaProcessor
import org.javiermf.db.meta.MetaResult.META_COMMAND_EXIT
import org.javiermf.db.meta.MetaResult.META_COMMAND_UNRECOGNIZED_COMMAND
import org.javiermf.db.statements.StatementProcessor
import org.javiermf.db.statements.UnrecognizedStatementException

class REPL(private val metaProcessor: MetaProcessor,
           private val statementProcessor: StatementProcessor,
           private val lineReader: LineReader) {

    fun start(){
        var mustExit = false
        var line: String

        while (!mustExit) {
            print(PROMPT_TEXT)
            line = lineReader.readInputLine()

            if (metaProcessor.isMetaCommand(line)) {
                val metaResult = metaProcessor.doMetaCommand(line)
                when (metaResult) {
                    META_COMMAND_EXIT -> mustExit = true
                    META_COMMAND_UNRECOGNIZED_COMMAND -> println("Unrecognized command '$line'")
                }
            } else {
                try {
                    statementProcessor.process(line)
                    println("Executed!")
                } catch (e: UnrecognizedStatementException) {
                    println("Unrecognized keyword at start of '$line'")
                }
            }
        }
    }
}