package org.javiermf.db.statements

import org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatementProcessorTests {

    private val statementProcessor = StatementProcessor()

    @Test
    fun shouldWorkWithValidStatement() {
        statementProcessor.process("select")
    }

    @Test
    fun shouldThrowExceptionIfNotRecognized() {
        assertThatExceptionOfType(UnrecognizedStatementException::class.java)
                .isThrownBy { statementProcessor.process("not an statement") }

    }

}