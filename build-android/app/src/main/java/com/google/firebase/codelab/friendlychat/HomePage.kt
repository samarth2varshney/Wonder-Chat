package com.google.firebase.codelab.friendlychat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.codelab.friendlychat.databinding.ActivityHomePageBinding
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }

        val sharedPreferences: SharedPreferences = getSharedPreferences("arrayListString", Context.MODE_PRIVATE)
        var arrayListString: String? = sharedPreferences.getString("arrayListString",null)
        var Groupnames = Gson().fromJson(arrayListString, ArrayList::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(Groupnames as ArrayList<String>)
        binding.recyclerView.adapter = adapter

        val mintent = Intent(this, MainActivity::class.java)
        adapter.setOnItemClickListener(object : RecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                mintent.putExtra("MESSAGES_CHILD",Groupnames[position])
                startActivity(mintent)
            }
        })

        binding.button2.setOnClickListener {
            startActivity(Intent(this,AddGroup::class.java))
        }


    }
}