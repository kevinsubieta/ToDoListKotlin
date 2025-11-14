package com.univalle.todolist.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.univalle.todolist.R
import com.univalle.todolist.databinding.ActivityAiactivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class AIActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAiactivityBinding

    private val client = OkHttpClient()

    private val apiKey = "PAST HERE YOU API KEY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAsk.setOnClickListener {
            val prompt = binding.etUserQuery.text.toString()
            if(prompt.isNotBlank()){
                binding.etResponse.text = "Generando respuesta...."
                sendPrompt(prompt)
            }
        }

    }

    private fun sendPrompt(prompt: String){
        lifecycleScope.launch {
            val response = generateResponse(prompt)
            binding.etResponse.text = response
        }
    }

    private suspend fun generateResponse(prompt: String, maxRetries: Int = 6)
    : String = withContext(Dispatchers.IO) {
        try{
            val content = JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", prompt)
                    })
                })
            }

            val root = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(content)
                })
            }

            val mediaType = "application/json".toMediaType()
            val body = root.toString().toRequestBody(mediaType)

            var lastErrorMsg = "ERROR"

            for(attempts in 1..maxRetries){
                val request = Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
                    .addHeader("x-goog-api-key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build()

                client.newCall(request).execute().use { response ->
                    val bodyString = response.body?.string() ?: ""
                    if(response.isSuccessful){
                        val data = JSONObject(bodyString)
                        val text = data
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text")

                        return@withContext text
                    }else{
                        lastErrorMsg = try {
                            val obj = JSONObject(bodyString)
                            obj.optJSONObject("error")?.optString("message")
                                ?: bodyString
                        } catch (e: Exception) {
                            bodyString.ifBlank { "HTTP" }
                        }

                        if(response.code == 503 && attempts < maxRetries){
                            val delayMs = 1000L * attempts * attempts
                            delay(delayMs)
                        }else{
                            return@withContext lastErrorMsg
                        }
                    }
                }
            }
            "Error"
        }catch (e: Exception){
            Log.e("AI Activity", "Error generating response")
            "Error"
        }
    }

}