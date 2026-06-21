package com.codegen.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

enum class Provider {
    GROQ,
    OLLAMA
}

@Service(Service.Level.APP)
@State(
    name = "CodegenSettings",
    storages = [Storage("codegen.xml")]
)
class AppSettings : PersistentStateComponent<AppSettings.State> {

    data class State(
        var provider: String = Provider.OLLAMA.name,
        var groqToken: String = ""
    )

    private var state = State()
    override fun getState() = state

    override fun loadState(state: State) {
        this.state = state
    }

    fun provider(): Provider = Provider.valueOf(state.provider)
    fun setProvider(provider: Provider) {
        state.provider = provider.name
    }

    fun groqToken(): String = state.groqToken
    fun setGroqToken(token: String) {
        state.groqToken = token
    }

    companion object {
        fun instance(): AppSettings = ApplicationManager.getApplication()
        .getService(AppSettings::class.java)
    }
}