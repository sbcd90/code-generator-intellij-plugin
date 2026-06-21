package com.codegen

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

class CursorAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val editor = event.getData(CommonDataKeys.EDITOR)?: return
        val project = event.project?: return

        generate(project, editor)
    }

    private fun clean(s: String): String {
        var sCleaned = s.trim()

        sCleaned = sCleaned.replace("```", "").replace("\n", "")
        sCleaned = sCleaned.replace(Regex("(?s)\\\\s*```\$"), "")

        if (sCleaned == "obj['next_code']") {
            return ""
        }
        return sCleaned.trim()
    }

    private fun generate(project: Project, editor: Editor) {
        val doc = editor.document
        val text = doc.text

        Thread {
            val client = Config.client()
            var generated = client.complete(text)
            generated = clean(generated)

            WriteCommandAction.runWriteCommandAction(project) {
                val offset = editor.caretModel.offset
                doc.insertString(offset, generated)
            }
        }.start()
    }
}