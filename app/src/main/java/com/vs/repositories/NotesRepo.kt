package com.vs.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentomojo.repository.Repo
import com.vs.app.TestApp
import com.vs.database.UserDatabase
import com.vs.models.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import com.vs.utils.Result
import io.reactivex.schedulers.Schedulers

/**
 * Created By Sachin
 */
class NotesRepo : Repo() {

    var notes: LiveData<List<User>>? = UserDatabase.getDatabase(TestApp.instance!!).notesDao().getAllNotes()

    private val _noteAddedOrUpdated = MutableLiveData<Result<User>>()
    val userAddedOrUpdated: LiveData<Result<User>> = _noteAddedOrUpdated


    fun addNote(context: Context, title: String, desc: String, time: String) {
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            Observable.just(UserDatabase.getDatabase(context).notesDao().insert(User(title, desc, time)))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it.subscribe {
                        _noteAddedOrUpdated.postValue(Result.Success(User(title, desc,time)))
                        _showProgressBar.postValue(false)
                    }

                })
    }

    fun deleteNote(context: Context, user: User) {
        Log.d("ComingHere", "Inside_deleteNote ${user.id}")
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            UserDatabase.getDatabase(context).notesDao().delete(user.id)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _showProgressBar.postValue(false)
                })

    }


    fun updateNote(context: Context, userData: User, time:String) {
        Log.d("ComingHere", "Inside_updateNote ${userData.id.toString() + " " + userData.title + " " + userData.description}")
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            UserDatabase.getDatabase(context).notesDao()
                    .update(userData.id, userData.title, userData.description,time)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _noteAddedOrUpdated.postValue(Result.Success(userData))
                    _showProgressBar.postValue(false)
                })

    }

    fun getNotes() {
        TestApp.instance?.also {
            _showProgressBar.postValue(true)
            disposables.add(Observable.fromCallable {
                Observable.just(UserDatabase.getDatabase(it).notesDao().getAllNotes())
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        _showProgressBar.postValue(false)
                        it.subscribe { data ->
                            Log.d("ComingHere", "Inside_showAllFruits ${listOf(notes)}")
                            notes = data
                        }

                    })
        }
    }


//
//
//    class NoteRepository(private val noteDao: NoteDao) {
//
//        private val allNotes: LiveData<List<User>> = noteDao.getAllNotes()
//
//        fun insert(user: User) {
//            InsertNoteAsyncTask(
//                    noteDao
//            ).execute(user)
//        }
//
//        fun deleteAllNotes() {
//            DeleteAllNotesAsyncTask(
//                    noteDao
//            ).execute()
//        }
//
//        fun getAllNotes(): LiveData<List<User>> {
//            return allNotes
//        }
//
//        private class InsertNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<User, Unit, Unit>() {
//
//            override fun doInBackground(vararg user: User?) {
//                noteDao.insert(user[0]!!)
//            }
//        }
//
//
//        private class DeleteAllNotesAsyncTask(val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {
//
//            override fun doInBackground(vararg p0: Unit?) {
//                noteDao.deleteAllNotes()
//            }
//        }
//
//    }


}
