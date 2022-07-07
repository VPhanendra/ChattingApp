package com.example.chattingApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatting_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var recycler :RecyclerView
    private lateinit var userlist :ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbrefer : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        dbrefer = FirebaseDatabase.getInstance().getReference()
        recycler =findViewById(R.id.user_recyclerView)
        userlist = ArrayList()
        adapter =UserAdapter(this,userlist)
        recycler.layoutManager =LinearLayoutManager(this)
        recycler.adapter=adapter
        dbrefer.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postsnapshort in snapshot.children){
                    val currentuser=postsnapshort.getValue(User::class.java)

                    //current user dosent show in the contact list
                    if (mAuth.currentUser?.uid !=currentuser?.uid) {
                        userlist.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            //write for logic for logout
                mAuth.signOut()
            val intent =Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}