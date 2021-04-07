package de.uriegel.activityextensions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import de.uriegel.ActivityExtensions.ActivityRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test.setOnClickListener {
            launch {
                val result = activityRequest.launch(Intent(this@MainActivity, TestActivity::class.java))
                Toast.makeText(this@MainActivity, "Ergebnis: ${result.resultCode}", Toast.LENGTH_LONG).show()
            }
        }
    }

    val activityRequest = ActivityRequest(this)
}