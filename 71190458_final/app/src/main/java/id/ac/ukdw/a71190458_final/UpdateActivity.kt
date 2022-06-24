package id.ac.ukdw.a71190458_final

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {

    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        val getJudul = intent.getStringExtra("judul").toString()
        val getGenre = intent.getStringExtra("genre").toString()
        val getProduser = intent.getStringExtra("namaProduser").toString()
        val getPemeranUtama = intent.getStringExtra("namaPemeranUtama").toString()
        val getTahun = intent.getStringExtra("tahun").toString()

        val edtJudul = findViewById<EditText>(R.id.edtUpdateJudul)
        val edtGenre = findViewById<EditText>(R.id.edtUpdateGenre)
        val edtProduser = findViewById<EditText>(R.id.edtUpdateNamaProduser)
        val edtPemeranUtama = findViewById<EditText>(R.id.edtUpdateNamaPemeranUtama)
        val edtTahun = findViewById<EditText>(R.id.edtUpdateTahun)

        val btnSave = findViewById<Button>(R.id.btnSimpan)

        edtJudul.setText(getJudul)
        edtGenre.setText(getGenre)
        edtProduser.setText(getProduser)
        edtPemeranUtama.setText(getPemeranUtama)
        edtTahun.setText(getTahun)

        btnSave.setOnClickListener {
            val updateFilm = Film(
                edtJudul.text.toString(),
                edtGenre.text.toString(),
                edtProduser.text.toString(),
                edtPemeranUtama.text.toString(),
                edtTahun.text.toString()
            )
            firestore?.collection("film")?.whereEqualTo("judul", getJudul)?.get()!!
                .addOnSuccessListener {
                    for (update in it) {
                        firestore?.collection("film")?.document(update.id)?.set(updateFilm)
                            ?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, "Update Berhasil", Toast.LENGTH_SHORT)
                                        .show()
                                    val i = Intent(this, MainActivity::class.java)
                                    startActivity(i)
                                }
                            }
                    }
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