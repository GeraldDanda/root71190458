package id.ac.ukdw.pertemuan9_71190458

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    var sp: SharedPreferences? = null
    var spEdit: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sp = getSharedPreferences("mySp", MODE_PRIVATE)
        spEdit = sp?.edit()

        if(sp?.getBoolean("isLogin", false) == true){
            setContentView(R.layout.activity_home)
            val btnLogout = findViewById<Button>(R.id.btnLogout)
            btnLogout.setOnClickListener {
                logout()
            }

            val spinBahasa  = findViewById<Spinner>(R.id.spnBahasa)
            val adapterArray = ArrayAdapter.createFromResource(this, R.array.list_bahasa, com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
            spinBahasa.adapter = adapterArray
            spinBahasa.setSelection(sp!!.getInt("bahasa",0))

            spinBahasa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                    spEdit?.putInt("bahasa", position)
                    spEdit?.commit()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            val spinFont  = findViewById<Spinner>(R.id.spnFont)
            val adapterArrayy = ArrayAdapter.createFromResource(this, R.array.list_font, com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
            spinFont.adapter = adapterArrayy
            spinFont.setSelection(sp!!.getInt("font",0))

            spinFont.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                    spEdit?.putInt("font", position)
                    spEdit?.commit()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }else{
            setContentView(R.layout.activity_main)
            val edtUsername = findViewById<EditText>(R.id.txUsername)
            val edtPassword = findViewById<EditText>(R.id.txPassword)
            val btnLogin = findViewById<Button>(R.id.btnLogin)
            btnLogin.setOnClickListener{
                login(edtUsername.text.toString(), edtPassword.text.toString())
            }
        }
    }

    fun login(username: String, password: String){
        if (username.equals("admin") && password.equals("12345")){
            spEdit?.putBoolean("isLogin", true)?.commit()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }else{
            Toast.makeText(this, "Username dan Password Salah", Toast.LENGTH_LONG).show()
        }
    }
    fun logout(){
        spEdit?.putBoolean("isLogin", false)?.commit()

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}