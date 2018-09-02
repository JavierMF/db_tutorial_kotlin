package org.javiermf.db

const val PROMPT_TEXT = "db > "
const val COMMAND_EXIT = ".exit"

fun main(args: Array<String>) {
    var mustExit = false
    var line: String

    while (!mustExit) {
        print(PROMPT_TEXT)
        line = readLine()!!.trim()

        if (line == COMMAND_EXIT) {
            mustExit = true
        } else {
            println("Unrecognized command '$line'")
        }
    }
}

