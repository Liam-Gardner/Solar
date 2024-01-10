package com.solar.data.repository

import com.solar.secrets.keyId
import com.solar.secrets.keySecret
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun calculateMD5Base64(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(input.toByteArray())
    return Base64.getEncoder().encodeToString(digest)
}

class CreateHeaders(
    content: String,
    resource: String,
    verb: String,
) {

    val contentMD5 = calculateMD5Base64(content)
    private val contentType = "application/json"
    val date: String =
        ZonedDateTime.now(ZoneId.of("GMT")).format(DateTimeFormatter.RFC_1123_DATE_TIME)

    private val signString = "$verb\n$contentMD5\n$contentType\n$date\n$resource"

    private val mac = Mac.getInstance("HmacSHA1")

    init {
        val secretKey = SecretKeySpec(keySecret.toByteArray(), "HmacSHA1")
        mac.init(secretKey)
    }

    private val hmacDigest: ByteArray = mac.doFinal(signString.toByteArray())

    private val sign: String = Base64.getEncoder().encodeToString(hmacDigest)

    val authorization = "API $keyId:$sign"

}