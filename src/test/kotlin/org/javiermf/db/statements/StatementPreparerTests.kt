package org.javiermf.db.statements

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatementPreparerTests {

    private val statementPreparer = StatementPreparer()

    @Test
    fun shouldPrepareASelect() {
        val statement = statementPreparer.prepareStatement("select")

        assertThat(statement)
                .isInstanceOf(StatementSelect::class.java)
    }

    @Test
    fun shouldPrepareACorrectInsert() {
        val statement = statementPreparer.prepareStatement("insert 2 javier mail@gmail.com")

        assertThat(statement)
                .isInstanceOf(StatementInsert::class.java)
        val insert = statement as StatementInsert
        assertThat(insert.row)
                .hasFieldOrPropertyWithValue("id", 2)
                .hasFieldOrPropertyWithValue("username", "javier")
                .hasFieldOrPropertyWithValue("email", "mail@gmail.com")
    }

    @Test
    fun shouldThrowExceptionIfLessInsertParameters() {
        assertThatExceptionOfType(PrepareSyntaxException::class.java)
                .isThrownBy { statementPreparer.prepareStatement("insert 2") }

    }

    @Test
    fun shouldThrowExceptionIfNotRecognized() {
        assertThatExceptionOfType(UnrecognizedStatementException::class.java)
                .isThrownBy { statementPreparer.prepareStatement("not an statement") }

    }

}