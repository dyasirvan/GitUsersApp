package com.android.gitusers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.gitusers.ui.setting.SettingsActivity
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var btnSettings: ImageView
    private lateinit var bottomNavigationView: AnimatedBottomBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        supportActionBar?.hide()

        btnSettings = findViewById(R.id.btn_settings)
        btnSettings.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navbar, menu)
        bottomNavigationView.setupWithNavController(menu!!, navController)
        return true
    }

}


