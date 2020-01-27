package com.vs.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vs.R
import com.vs.models.User
import com.vs.utils.RxBus
import com.vs.veronica.utils.C
import com.vs.views.activities.HomeActivity
import com.vs.views.fragments.NoteDetailsFragment
import kotlinx.android.synthetic.main.layout_note_item.view.*


/**
 *  Created by Sachin
 */
class NotesAdapter(private val context: Context, private val users: List<User>) :
        androidx.recyclerview.widget.RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindToView(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(itemView: View) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val tvNoteTitle = itemView.tvNoteTitle!!
        private val tvNoteDesc = itemView.tvNoteDesc!!
        private val cvNoteItemContainer = itemView.cvNoteItemContainer!!

        fun bindToView(user: User) {
            tvNoteTitle.text = user.title
            tvNoteDesc.text = user.description
            cvNoteItemContainer.setOnClickListener { goToNoteDetailsScreen(user) }
            cvNoteItemContainer.setOnLongClickListener {
                RxBus.showActionDailog.onNext(user)
                true
            }
        }
    }

    private fun goToNoteDetailsScreen(user: User) {
        val noteDetailsFragment = NoteDetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable(C.NOTE, user)
        noteDetailsFragment.arguments = bundle

        (context as HomeActivity).also {
            it.supportFragmentManager.beginTransaction()
                    .add(R.id.rlContainer, noteDetailsFragment, C.NOTE_DETAILS).addToBackStack("NoteDetailsFragment")
                    .commitAllowingStateLoss()
        }
    }
}
