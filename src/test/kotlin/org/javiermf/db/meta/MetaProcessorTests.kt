package org.javiermf.db.meta

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MetaProcessorTests {

    private val metaProcessor = MetaProcessor()

    @Test
    fun shouldDetectACommand() {
        assertThat(metaProcessor.isMetaCommand(".command")).isTrue()
    }

    @Test
    fun shouldDetectAnStatement() {
        assertThat(metaProcessor.isMetaCommand("select *")).isFalse()
    }

    @Test
    fun shouldExit() {
        assertThat(metaProcessor.doMetaCommand(".exit")).isEqualTo(MetaResult.META_COMMAND_EXIT)
    }

    @Test
    fun shouldUnrecognizeElse() {
        assertThat(metaProcessor.doMetaCommand(".wtf")).isEqualTo(MetaResult.META_COMMAND_UNRECOGNIZED_COMMAND)
    }

}