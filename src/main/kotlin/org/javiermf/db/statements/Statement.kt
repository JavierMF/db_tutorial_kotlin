package org.javiermf.db.statements

import org.javiermf.db.data.Row

interface Statement

data class StatementInsert(val row: Row) : Statement
class StatementSelect : Statement