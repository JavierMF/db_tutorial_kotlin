package org.javiermf.db.statements

import org.javiermf.db.data.Table

class StatementExecutor(private val table: Table) {

    fun executeStatement(statement: Statement) {
        when (statement) {
            is StatementInsert -> executeInsert(statement)
            is StatementSelect -> executeSelect(statement)
        }
    }

    private fun executeSelect(statement: StatementSelect) {
        for (row in table.getAllRows()) {
            println("${row.id} ${row.username} ${row.email}")
        }
    }

    private fun executeInsert(statement: StatementInsert) {
        table.addRow(statement.row)
    }
}