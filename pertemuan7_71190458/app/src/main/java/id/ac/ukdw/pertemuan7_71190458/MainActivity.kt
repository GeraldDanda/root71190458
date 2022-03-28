package id.ac.ukdw.pertemuan7_71190458

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listContact = arrayListOf<Contact>()
        listContact.add(Contact("Juu", R.mipmap.juu, "085212324335", "rahuloscar3@gmail.com"))
        listContact.add(Contact("John", R.mipmap.john, "081354237646", "jopat13@gmail.com"))
        listContact.add(Contact("Patrick", R.mipmap.patrick, "081223653587", "palet4@gmail.com"))


        val rvContact = findViewById<RecyclerView>(R.id.rvContact)
        rvContact.layoutManager = LinearLayoutManager(this)
        val contactAdapter = ContactAdapter(listContact)
        rvContact.adapter = contactAdapter
    }
}