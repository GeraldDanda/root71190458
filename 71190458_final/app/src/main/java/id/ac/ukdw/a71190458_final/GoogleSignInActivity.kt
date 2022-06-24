package id.ac.ukdw.a71190458_final

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.ac.ukdw.a71190458_final.databinding.ActivityGoogleSignInBinding


class GoogleSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoogleSignInBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null){
            binding.tvEmail.setText(user.email)
        }

//        binding.textDisplayName.text = intent.getStringExtra(EXTRA_NAME)
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
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