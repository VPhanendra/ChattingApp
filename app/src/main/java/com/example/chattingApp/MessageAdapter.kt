package com.example.chattingApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatting_app.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val message : ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_SENT=1
    val ITEM_RECEIVE=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            // inflate receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.receiver,parent,false)
            return ReceiverViewHolder(view)
        }else{
            // inflate sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentUser = message[position]
        if (holder.javaClass == SendViewHolder::class.java){
          //do the stuff for sent view holder
        val viewHolder =holder as SendViewHolder
          holder.sentMessage.text=currentUser.message
      }else{
          //do the stuff for receiver view holder

          val viewHolder =holder as ReceiverViewHolder
            holder.receiverMessage.text=currentUser.message
      }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = message[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }
    override fun getItemCount(): Int {
        return message.size
    }
    class SendViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
      val sentMessage =itemView.findViewById<TextView>(R.id.text_send)
    }
    class ReceiverViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val receiverMessage =itemView.findViewById<TextView>(R.id.text_receiver)

    }


}