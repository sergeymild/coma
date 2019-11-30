package com.android.samplecoma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.coma.Coma

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Coma.withActivity(this)
            .with(20, Intent(Intent.ACTION_GET_CONTENT))
            .withRequestHandler { result -> println("${result.requestCode} ${result.resultCode} ${result.data}") }
            .startForResult()
    }
}
