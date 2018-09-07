package org.javiermf.db.statements

class StatementProcessor(private val executor: StatementExecutor,
                         private val preparer: StatementPreparer) {

    fun process(line: String) {
        val statement = preparer.prepareStatement(line)
        executor.executeStatement(statement)
    }

}
