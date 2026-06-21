package com.codegen

import GroqClient
import com.codegen.settings.AppSettings

object GroqLlmClient : LlmClient {
    override fun complete(prompt: String): String {
        val token = AppSettings.instance().groqToken()
        require(token.isNotBlank()) { "Groq token is blank" }
        return GroqClient.complete(prompt, token)
    }
}