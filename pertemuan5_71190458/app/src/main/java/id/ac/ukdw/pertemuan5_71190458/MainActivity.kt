package id.ac.ukdw.pertemuan5_71190458

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = intent.getStringExtra("user")
        val txUser = findViewById<TextView>(R.id.user)
        txUser.text = "Selamat Datang ${user}"
    }
}