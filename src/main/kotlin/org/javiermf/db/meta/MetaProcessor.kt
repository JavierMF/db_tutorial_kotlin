package org.javiermf.db.meta

import org.javiermf.db.meta.MetaResult.*


const val COMMAND_EXIT = ".exit"


class MetaProcessor {

    fun doMetaCommand(line: String): MetaResult {
        return when (line) {
            COMMAND_EXIT -> META_COMMAND_EXIT
            else -> MetaResult.META_COMMAND_UNRECOGNIZED_COMMAND
        }
    }

    fun isMetaCommand(line: String): Boolean {
        return line[0] == '.'
    }
}

