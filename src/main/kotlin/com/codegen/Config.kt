package com.codegen

import com.codegen.settings.AppSettings
import com.codegen.settings.Provider

object Config {
    fun client(): LlmClient {
        return when (
            AppSettings.instance().provider()
        ) {
            Provider.GROQ -> GroqLlmClient
            Provider.OLLAMA -> OllamaLlmClient
        }
    }
}