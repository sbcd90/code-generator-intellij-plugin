import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object GroqClient {
    private val client = OkHttpClient()
    private const val MODEL = "llama-3.3-70b-versatile"

    fun complete(code: String, token: String): String {
        println("Hit Groq Client")
        val prompt = """
You are an IDE autocomplete engine.

Generate code inserted at <CURSOR>.

Rules:
- Output ONLY source code
- No markdown
- No ```
- No explanations
- No placeholders
- No repeated context
- Preserve indentation
- Return inserted lines only

Context:

$code <CURSOR>
""".trimIndent()
        val body = JSONObject()
            .put("model", MODEL)
            .put("temperature", 0.1)
            .put("max_completion_tokens", 256)
            .put("stop", JSONArray().put("```").put("<|im_end|>"))
            .put("messages", JSONArray().put(JSONObject().put("role", "user").put("content", prompt)))

        val request = Request.Builder()
            .url("https://api.groq.com/openai/v1/chat/completions")
            .header("Authorization", "Bearer $token")
            .header("Content-Type", "application/json")
            .post(body.toString().toRequestBody("application/json".toMediaType()))
        .build()

        client.newCall(request).execute()
            .use {
                val json = JSONObject(it.body!!.string())
                val text = json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                return clean(text)
            }
    }

    private fun clean(s: String): String {
        var result = s.trim()

        result = result.replace(Regex("(?s)^```\\w*\\s*"), "")
        result = result.replace(Regex("(?s)\\s*```$"), "")
        if (result == "obj['next_code']") {
            return ""
        }
        return result.trim()
    }
}