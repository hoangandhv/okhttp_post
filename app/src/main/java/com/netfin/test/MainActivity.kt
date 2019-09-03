package com.netfin.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat.getExtras
import com.google.zxing.integration.android.IntentResult
import android.content.Intent
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Picasso.get()
            .load("https://i.imgur.com/60IA8cW.jpg")
            .placeholder(R.drawable.capture)
            .into(imageView)
        /*Glide.with(this)
            .load("https://i.imgur.com/60IA8cW.jpg")
            .into(imageView)*/
        /*val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialog)
        builder.show()*/

        //var request = OkHttpRequest(client)
        val url = "http://10.10.10.43/api/upload"
        val map: HashMap<String, String> = hashMapOf("fistNname" to "Rohan", "lastName" to "Jahagirdar", "profession" to "Student")
        POST(url, map, object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                runOnUiThread{
                    try {
                        var json = JSONObject(responseData)
                        println("Request Successful!!")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request Failure.")
            }
        })
    }
    fun POST(url: String, parameters: HashMap<String, String>, callback: Callback): Call {
        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val formBody = builder.build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()


        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }


}
