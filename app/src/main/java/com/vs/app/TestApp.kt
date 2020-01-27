package com.vs.app

import androidx.multidex.MultiDexApplication

/**
 * Created by Sachin
 */
class TestApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private val TAG = TestApp::class.java.name
        @get:Synchronized
        var instance: TestApp? = null
    }
}
