package com.codegen.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.util.NlsContexts
import javax.swing.JComponent

class CodegenConfigurable : Configurable {
    private val ui = SettingsComponent()

    override fun getDisplayName() = "Codegen"

    override fun createComponent(): JComponent {
        return ui.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettings.instance()

        return (settings.provider() != ui.provider() ||
                settings.groqToken() != ui.groqToken())
    }

    override fun apply() {
        val settings = AppSettings.instance()
        settings.setProvider(ui.provider())
        settings.setGroqToken(ui.groqToken())
    }

    override fun reset() {
        val settings = AppSettings.instance()
        ui.setProvider(settings.provider())
        ui.setGroqToken(settings.groqToken())
    }
}