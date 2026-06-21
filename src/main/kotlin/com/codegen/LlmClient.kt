package com.codegen

interface LlmClient {
    fun complete(prompt: String): String
}