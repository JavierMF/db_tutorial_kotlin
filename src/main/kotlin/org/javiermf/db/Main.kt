package org.javiermf.db

import org.javiermf.db.meta.MetaProcessor
import org.javiermf.db.repl.REPL
import org.javiermf.db.repl.StdinLineReader
import org.javiermf.db.statements.StatementProcessor

const val PROMPT_TEXT = "db > "

fun main(args: Array<String>) {

    val metaProcessor = MetaProcessor()
    val statementProcessor = StatementProcessor()
    val lineReader = StdinLineReader()
    val repl = REPL(metaProcessor, statementProcessor, lineReader)

    repl.start()

}


