package com.example.iberdrola.data_ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class KtorHelper {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getData(): String {
        return client.get("https://viewnextandroid4.wiremockapi.cloud/facturas")
            .body<KtorResponse>().toString()
    }
}