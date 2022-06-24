package id.ac.ukdw.a71190458_final

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth

    var firestore: FirebaseFirestore? = null
    var listFilm = arrayListOf<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()

//        auth = FirebaseAuth.getInstance()

        //variable untuk pencarian
        val btnCari = findViewById<ImageButton>(R.id.btnCari)
        val edtCari = findViewById<EditText>(R.id.edtSearch)
        val rvFilm = findViewById<RecyclerView>(R.id.rvFilm)
        val btnAdd = findViewById<FloatingActionButton>(R.id.fab_add)


        btnAdd.setOnClickListener {
            val i = Intent(this, CreateActivity::class.java)
            startActivity(i)
        }


        val loading = ProgressDialog(this)
        loading.setMessage("Loading Data...")
        loading.show()
        firestore?.collection("film")?.get()?.addOnSuccessListener { docs ->
            var hasil = ""
            for (doc in docs) {
                hasil += "${doc["judul"]}"
                val filmAdd = Film(
                    "${doc["judul"]}",
                    "${doc["genre"]}",
                    "${doc["namaProduser"]}",
                    "${doc["namaPemeranUtama"]}",
                    "${doc["tahun"]}"
                )
                listFilm.add(filmAdd)
            }
            loading.dismiss()
        }
        Handler().postDelayed({
            rvFilm.layoutManager = LinearLayoutManager(this)
            val adapter = FilmAdapter(listFilm, this)
            rvFilm.adapter = adapter
        }, 1000)

        btnCari.setOnClickListener {
            var pencarian = edtCari.text.toString()
            if (pencarian.isEmpty()) {
                Toast.makeText(this, "Pencarian Kosong", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    rvFilm.layoutManager = LinearLayoutManager(this)
                    val adapter = FilmAdapter(listFilm, this)
                    rvFilm.adapter = adapter
                }, 1000)
            } else if (!pencarian.isEmpty()) {
                loading.setMessage("Mencari...")
                loading.show()
                listFilm.clear()
                firestore?.collection("film")?.get()?.addOnSuccessListener { docs ->
                    for (cari in docs) {
                        var bool = true
                        val filmCari = Film(
                            "${cari["judul"]}",
                            "${cari["genre"]}",
                            "${cari["namaProduser"]}",
                            "${cari["namaPemeranUtama"]}",
                            "${cari["tahun"]}"
                        )
                        if (pencarian.equals("${cari["judul"]}") && bool) {
                            Toast.makeText(this, "Pencarian Ditemukan", Toast.LENGTH_SHORT).show()
                            bool = false
                            listFilm.add(filmCari)
                        }
                        if (pencarian.equals("${cari["genre"]}") && bool) {
                            Toast.makeText(this, "Pencarian Ditemukan", Toast.LENGTH_SHORT).show()
                            bool = false
                            listFilm.add(filmCari)
                        }
                        if (pencarian.equals("${cari["namaProduser"]}") && bool) {
                            Toast.makeText(this, "Pencarian Ditemukan", Toast.LENGTH_SHORT).show()
                            bool = false
                            listFilm.add(filmCari)
                        }
                        if (pencarian.equals("${cari["namaPemeranUtama"]}") && bool) {
                            Toast.makeText(this, "Pencarian Ditemukan", Toast.LENGTH_SHORT).show()
                            bool = false
                            listFilm.add(filmCari)
                        }
                        if (pencarian.equals("${cari["tahun"]}") && bool) {
                            Toast.makeText(this, "Pencarian Ditemukan", Toast.LENGTH_SHORT).show()
                            bool = false
                            listFilm.add(filmCari)
                        }
                    }
                }
                loading.dismiss()
                Handler().postDelayed({
                    rvFilm.layoutManager = LinearLayoutManager(this)
                    val adapter = FilmAdapter(listFilm, this)
                    rvFilm.adapter = adapter
                },1000)
            }
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tollbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when(item.itemId){
            R.id.ic_home -> {
                val home = Intent(this, MainActivity::class.java)
                startActivity(home)
                this.finish()
            }
            R.id.ic_profile -> {
                val profile = Intent(this, GoogleSignInActivity::class.java)
                startActivity(profile)
            }
            R.id.ic_logout -> {
                Firebase.auth.signOut()
                val logout = Intent(this, LoginActivity::class.java)
                startActivity(logout)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}