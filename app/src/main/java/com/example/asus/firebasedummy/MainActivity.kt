package com.example.asus.firebasedummy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRemoteConfig()
        displayButton()

    }

    private fun setupRemoteConfig() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
    }

    private fun displayButton() {
        if (remoteConfig.getBoolean(SHOW_BUTTON)) {
            button.visibility = View.VISIBLE
            //button.text = remoteConfig.getString(BUTTON1_LABEL)
            button.text = "test"
//            button.setOnClickListener {
//                val intent: Intent = try {
//                    Intent(this,
//                            Class.forName(remoteConfig.getString(ACTION_BUTTON1)))
//                } catch (e: ClassNotFoundException) {
//                    Intent(this, CatActivity::class.java)
//                }
//                startActivity(intent)
//            }
        } else {
            button.visibility = View.GONE
        }
    }

    private fun setupRemoteConfig2() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        remoteConfig.setConfigSettings(configSettings)
        remoteConfig.setDefaults(R.xml.default_config)

        remoteConfig.fetch(0)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Fetch Succeeded", Toast.LENGTH_SHORT).show()
                        remoteConfig.activateFetched()
                    } else {
                        Toast.makeText(this, "Fetch Failed", Toast.LENGTH_SHORT).show()
                    }
                    displayButton()
                }
    }
}
