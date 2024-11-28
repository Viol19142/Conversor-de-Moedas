package com.example.conversordemoedas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_list_resources : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_resources)

        val rvResources: RecyclerView = findViewById(R.id.rv_resources)
        rvResources.layoutManager = LinearLayoutManager(this)

        val resourcesList = WalletManager.currencies.map { (key, value) ->
            "$key: ${"%.2f".format(value)}"
        }

        rvResources.adapter = ResourcesAdapter(resourcesList)
    }
}