package de.uriegel.activityextensions.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.net.Authenticator
import java.net.HttpURLConnection
import java.net.PasswordAuthentication
import java.net.URL
import java.util.zip.GZIPInputStream

fun basicAuthentication(name: String, pw: String) {
    class BasicAuthenticator : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(name, pw.toCharArray())
        }
    }
    Authenticator.setDefault(BasicAuthenticator())
}

suspend fun getString(urlString: String): String {

    return withContext(Dispatchers.IO) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty("Accept-Encoding", "gzip")
        connection.connect()
        val inStream = GZIPInputStream(connection.inputStream)
        return@withContext readStream(inStream)
    }
}

suspend fun post(urlString: String, data: String): String {
    return post(urlString, data, null)
}

suspend fun post(urlString: String, data: String, psk: String?): String {
    return withContext(Dispatchers.IO) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        if (psk != null)
            connection.setRequestProperty("X-Auth-PSK", psk)
        connection.doInput = true
        val writer = BufferedWriter(OutputStreamWriter(connection.outputStream))
        writer.write(data)
        writer.close()
        return@withContext readStream(connection.inputStream)
    }
}

private fun readStream(inString: InputStream): String {
    val response = StringBuffer()
    val reader = BufferedReader(InputStreamReader(inString))
    var line: String?
    while (reader.readLine().also { line = it } != null)
        response.append(line)
    reader.close()
    return response.toString()
}

