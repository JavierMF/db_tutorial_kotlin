package org.javiermf.db

import org.javiermf.db.data.Table
import org.javiermf.db.meta.MetaProcessor
import org.javiermf.db.repl.REPL
import org.javiermf.db.repl.StdinLineReader
import org.javiermf.db.statements.StatementExecutor
import org.javiermf.db.statements.StatementPreparer
import org.javiermf.db.statements.StatementProcessor

const val PROMPT_TEXT = "db > "

fun main(args: Array<String>) {

    val table = Table()
    val statementExecutor = StatementExecutor(table)
    val metaProcessor = MetaProcessor()
    val statementPreparer = StatementPreparer()
    val statementProcessor = StatementProcessor(statementExecutor, statementPreparer)
    val lineReader = StdinLineReader()
    val repl = REPL(metaProcessor, statementProcessor, lineReader)

    repl.start()

}


