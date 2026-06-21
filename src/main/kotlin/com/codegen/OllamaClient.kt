import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object OllamaClient {
    private val client = OkHttpClient()

    fun complete(code: String): String {
        println("Hit Ollama Client")
        val prompt = """
You are an IDE autocomplete engine.

Task:
Generate code that should be inserted at <CURSOR>.

Requirements:
- Output ONLY source code
- NEVER output markdown
- NEVER output ``` or language tags
- NEVER output placeholders
- NEVER output examples
- NEVER output text outside code
- NEVER repeat existing code
- Return only the inserted lines

Context:

$code <CURSOR>
"""
        val body = JSONObject()
            .put("model", "qwen2.5-coder:1.5b")
            .put("stream", false)
            .put("prompt", prompt)

        val request = Request.Builder()
            .url("http://localhost:11434/api/generate")
            .post(body.toString().toRequestBody(
                "application/json".toMediaType()))
            .build()

        client.newCall(request)
            .execute()
            .use {
                val json = JSONObject(it.body!!.string())
                return json.getString("response")
            }
    }
}