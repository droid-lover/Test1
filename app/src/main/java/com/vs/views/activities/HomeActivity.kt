package com.vs.views.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vs.R
import com.vs.utils.RxBus
import com.vs.veronica.utils.C
import com.vs.views.fragments.ShowDataFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHomeFragment()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        baseContext.resources.updateConfiguration(newConfig, baseContext.resources.displayMetrics);
        setContentView(R.layout.activity_home)

    }

    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rlContainer, ShowDataFragment(), "ShowDataFragment")
            .commitAllowingStateLoss()
    }


    /*-- handling back press here --*/
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag(C.NOTE_DETAILS) == null) {
            super.onBackPressed()
        } else {
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        }
    }
}
