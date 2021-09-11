package de.uriegel.activityextensions

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import de.uriegel.activityextensions.async.delay
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

        permissions.setOnClickListener {
            launch {
                val result = activityRequest.checkAndAccessPermissions(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ))
                Toast.makeText(this@MainActivity, "Ergebnis: ${result.toString()}", Toast.LENGTH_LONG).show()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    delay(15000)

                    val result2 =
                        activityRequest.checkAndAccessPermissions(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
                    Toast.makeText(this@MainActivity,  "Ergebnis: ${result2.toString()}", Toast.LENGTH_LONG ).show()
                }
            }
        }
    }

    val activityRequest = ActivityRequest(this)
}