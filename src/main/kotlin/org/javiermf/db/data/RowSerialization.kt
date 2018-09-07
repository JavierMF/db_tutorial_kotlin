package org.javiermf.db.data

import java.nio.ByteBuffer
import java.nio.charset.Charset

const val idSize = java.lang.Integer.BYTES
const val userNameSize = 32
const val emailSize = 255
const val rowSize = idSize + userNameSize + emailSize

const val idOffset = 0
const val userNameOffset = idOffset + idSize
const val emailOffset = userNameOffset + userNameSize

fun Row.serialize(): ByteArray {

    val usernameByteArray = ByteArray(userNameSize)
    val usernameBytes = this.username.toByteArray(Charset.defaultCharset())
    for (i in 0..(usernameBytes.size - 1)) {
        usernameByteArray[i] = usernameBytes[i]
    }

    val emailByteArray = ByteArray(emailSize)
    val emailBytes = this.email.toByteArray(Charset.defaultCharset())
    for (i in 0..(emailBytes.size - 1)) {
        emailByteArray[i] = emailBytes[i]
    }


    val byteBuffer = ByteBuffer.allocate(rowSize)
            .putInt(this.id)
            .put(usernameByteArray)
            .put(emailByteArray)

    val bytesToRead = ByteArray(rowSize)
    byteBuffer.position(0)
    byteBuffer.get(bytesToRead)
    return bytesToRead
}

fun ByteArray.deserializeRow(): Row {
    val byteBuffer = ByteBuffer.allocate(rowSize).put(this)

    val id = byteBuffer.getInt(idOffset)
    val userName = deserializeString(byteBuffer, userNameOffset, userNameSize)
    val email = deserializeString(byteBuffer, emailOffset, emailSize)

    return Row(id, userName, email)
}

private fun deserializeString(byteBuffer: ByteBuffer, pos: Int, size: Int): String {
    val stringBytes = ByteArray(size)
    byteBuffer.position(pos)
    byteBuffer.get(stringBytes)
    return String(stringBytes, Charset.defaultCharset()).replace("\u0000", "", true)
}
