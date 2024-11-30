package com.example.conversordemoedas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class activity_list_resources : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_resources)

        val rvResources: RecyclerView = findViewById(R.id.rv_resources)
        val btnBack: Button = findViewById(R.id.btn_back)
        rvResources.layoutManager = LinearLayoutManager(this)

        val resourcesList = WalletManager.currencies.map { (key, value) ->
            "$key: ${"%.2f".format(value)}"
        }.toMutableList()

        rvResources.adapter = ResourcesAdapter(resourcesList)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
