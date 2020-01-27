package com.vs.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.vs.R
import com.vs.models.User
import com.vs.utils.Result
import com.vs.utils.Utils
import com.vs.veronica.utils.C
import com.vs.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*


/**
 * Created By Sachin
 */
class AddNoteFragment : Fragment() {

    private val notesViewModel by lazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }
    private var userData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            userData = it.getSerializable(C.NOTE) as User
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        if (userData != null) setNote(userData!!) else btnSaveNote?.text = "Add"
        btnSaveNote.setOnClickListener {
            saveOrUpdateNote(btnSaveNote.text)
        }
    }

    private fun setNote(user: User) {
        noteTitleTextInputEditText?.setText(user.title)
        noteDescTextInputEditText?.setText(user.description)
        btnSaveNote?.text = "Update"
    }

    private fun saveOrUpdateNote(text: CharSequence) {
        val title = noteTitleTextInputEditText?.text.toString()
        val desc = noteDescTextInputEditText?.text.toString()
        if (title.isEmpty() || desc.isEmpty()) {
            Utils.showToastMessage("Please enter required details!")
            return
        }

        if (text == "Add")
            activity?.also { notesViewModel.addNote(it, title, desc,Utils.getTime()) }
        else if (text == "Update")
            userData?.also {
                it.title = title
                it.description = desc
                activity?.also { context -> notesViewModel.updateNote(context, it,Utils.getTime()) }
            }

    }

    private fun observeViewModelChanges() {
        notesViewModel.noteAddedOrUpdated.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Result.Success<User> -> {
                    goToNoteDetailsScreen(it.data)
                }
                is Result.Failure -> {
                    Utils.showToastMessage("Something went wrong ${it.throwable.localizedMessage}")
                }
            }
        })
    }

    private fun goToNoteDetailsScreen(user: User) {
        val noteDetailsFragment = NoteDetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable(C.NOTE, user)
        noteDetailsFragment.arguments = bundle

        activity?.also {
            it.supportFragmentManager.beginTransaction()
                    .add(R.id.rlContainer, noteDetailsFragment, C.NOTE_DETAILS).addToBackStack("NoteDetailsFragment")
                    .commitAllowingStateLoss()
        }
    }

}
