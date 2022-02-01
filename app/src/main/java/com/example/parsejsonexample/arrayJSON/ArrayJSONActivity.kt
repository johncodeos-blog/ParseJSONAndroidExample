package com.example.parsejsonexample.arrayJSON

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parsejsonexample.Cell
import com.example.parsejsonexample.R
import com.example.parsejsonexample.RVAdapter
import com.example.parsejsonexample.databinding.ActivityArrayJsonBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONTokener
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

@DelicateCoroutinesApi
class ArrayJSONActivity : AppCompatActivity() {

    var itemsArray: ArrayList<Cell> = ArrayList()
    lateinit var adapter: RVAdapter

    private lateinit var binding: ActivityArrayJsonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArrayJsonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Clean TextViews
        binding.jsonResultsTextview.text = ""

        setupRecyclerView()
        parseJSON()

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.jsonResultsRecyclerview.layoutManager = layoutManager
        binding.jsonResultsRecyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
                DividerItemDecoration(
                        binding.jsonResultsRecyclerview.context,
                        layoutManager.orientation
                )
        ContextCompat.getDrawable(this, R.drawable.line_divider)?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        binding.jsonResultsRecyclerview.addItemDecoration(dividerItemDecoration)
    }

    private fun parseJSON() {
        GlobalScope.launch(Dispatchers.IO) {
            val url =
                    URL("https://raw.githubusercontent.com/johncodeos-blog/ParseJSONAndroidExample/main/array.json")
            val httpsURLConnection = url.openConnection() as HttpsURLConnection
            httpsURLConnection.setRequestProperty(
                    "Accept",
                    "application/json"
            ) // The format of response we want to get from the server
            httpsURLConnection.requestMethod = "GET"
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = false
            // Check if the connection is successful
            val responseCode = httpsURLConnection.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val response = httpsURLConnection.inputStream.bufferedReader()
                        .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    Log.d("Pretty Printed JSON :", prettyJson)
                    binding.jsonResultsTextview.text = prettyJson

                    val jsonArray = JSONTokener(response).nextValue() as JSONArray
                    for (i in 0 until jsonArray.length()) {
                        // ID
                        val id = jsonArray.getJSONObject(i).getString("id")
                        Log.i("ID: ", id)

                        // Employee Name
                        val employeeName = jsonArray.getJSONObject(i).getString("employee_name")
                        Log.i("Employee Name: ", employeeName)

                        // Employee Salary
                        val employeeSalary = jsonArray.getJSONObject(i).getString("employee_salary")
                        Log.i("Employee Salary: ", employeeSalary)

                        // Employee Age
                        val employeeAge = jsonArray.getJSONObject(i).getString("employee_age")
                        Log.i("Employee Age: ", employeeAge)

                        val model =
                                Cell(id, employeeName, "$ $employeeSalary", employeeAge)
                        itemsArray.add(model)

                        adapter = RVAdapter(itemsArray)
                        adapter.notifyDataSetChanged()
                    }
                }

                // Pass the Array with data to RecyclerView Adapter
                binding.jsonResultsRecyclerview.adapter = adapter

            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }
    }
}


