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


class CreateActivity: AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        val edtJudul = findViewById<EditText>(R.id.edtJudul)
        val edtGenre = findViewById<EditText>(R.id.edtGenre)
        val edtNamaProduser = findViewById<EditText>(R.id.edtNamaProduser)
        val edtNamaPemeranUtama = findViewById<EditText>(R.id.edtNamaPemeranUtama)
        val edtTahun = findViewById<EditText>(R.id.edtTahun)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val film = Film(
                edtJudul.text.toString(), edtGenre.text.toString(),
                edtNamaProduser.text.toString(), edtNamaPemeranUtama.text.toString(),
                edtTahun.text.toString()
            )

            edtJudul.setText("")
            edtGenre.setText("")
            edtNamaProduser.setText("")
            edtNamaPemeranUtama.setText("")
            edtTahun.setText("")
            firestore?.collection("film")?.add(film)?.addOnSuccessListener {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                Toast.makeText(this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show()
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