package com.codegen

object OllamaLlmClient : LlmClient {
    override fun complete(prompt: String): String {
        return OllamaClient.complete(prompt)
    }
}