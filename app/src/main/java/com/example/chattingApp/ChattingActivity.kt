package com.example.chattingApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatting_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChattingActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageTextView: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagelist : ArrayList<Message>
    private lateinit var mDbref :DatabaseReference
    var receiverroom :String? =null
    var senderroom :String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        val name =intent.getStringExtra("name")
        val receiveruid =intent.getStringExtra("uid")
            val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbref =FirebaseDatabase.getInstance().getReference()
        senderroom =receiveruid+senderUid
        receiverroom =senderUid+receiveruid
        supportActionBar?.title =name
        messageRecyclerView =findViewById(R.id.messageRecylcerview)
        messageTextView = findViewById(R.id.messagebox)
        sendButton =findViewById(R.id.imagebutton)
        messagelist =ArrayList()
        messageAdapter= MessageAdapter(this,messagelist)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter =messageAdapter
        // logic for adding data to recyclerview
        mDbref.child("chats").child(senderroom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                   for (postsnapshort in snapshot.children){
                       val message =postsnapshort.getValue(Message::class.java)
                       messagelist.add(message!!)
                   }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

//adding the message to database
        sendButton.setOnClickListener {
            val message = messageTextView.text.toString()
            val messageObject = Message(message,senderUid)
            mDbref.child("chats").child(senderroom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiverroom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageTextView.setText("")

        }

    }
}