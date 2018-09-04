package org.javiermf.db.repl

class StdinLineReader : LineReader {

    override fun readInputLine(): String {
        return readLine()!!.trim()
    }
}