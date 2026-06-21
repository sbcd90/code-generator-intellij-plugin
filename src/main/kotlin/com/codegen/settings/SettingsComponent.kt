package com.codegen.settings

import com.intellij.openapi.ui.ComboBox
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import java.awt.Insets
import javax.swing.*

class SettingsComponent {
    val providerBox = ComboBox(arrayOf(Provider.GROQ, Provider.OLLAMA))

    private val tokenLabel = JLabel("Groq Token")

    private val tokenField = JPasswordField(40)

    val panel = JPanel(GridBagLayout())

    init {

        val c = GridBagConstraints()
        c.insets = Insets(8, 8, 8, 8)
        c.anchor = GridBagConstraints.WEST
        c.fill = GridBagConstraints.HORIZONTAL

        // Provider label
        c.gridx = 0
        c.gridy = 0
        panel.add(JLabel("Provider"), c)

        // Provider dropdown
        c.gridx = 1
        c.weightx = 1.0
        providerBox.preferredSize =
            providerBox.preferredSize.apply {
                width = 250
            }

        panel.add(providerBox, c)

        // Token label
        c.gridx = 0
        c.gridy = 1
        c.weightx = 0.0

        val tokenLabel =
            JLabel("Groq Token")

        panel.add(tokenLabel, c)

        // Token field
        c.gridx = 1
        c.weightx = 1.0

        panel.add(tokenField, c)

        providerBox.addActionListener {
            val show =
                provider() == Provider.GROQ

            tokenLabel.isVisible = show
            tokenField.isVisible = show

            panel.revalidate()
            panel.repaint()
        }

        setProvider(
            AppSettings
                .instance()
                .provider()
        )

        setGroqToken(
            AppSettings
                .instance()
                .groqToken()
        )
    }

    fun provider(): Provider = providerBox.selectedItem as Provider
    fun setProvider(provider: Provider) {
        providerBox.selectedItem = provider
    }

    fun groqToken(): String = String(tokenField.password)
    fun setGroqToken(token: String) {
        tokenField.text = token
    }
}