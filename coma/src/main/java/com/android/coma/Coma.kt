package com.android.coma

import android.content.Intent
import android.os.Build
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class ResultRequest(val requestCode: Int, val intent: Intent)
class Result(val requestCode: Int, val resultCode: Int, val data: Intent?)

typealias ResultHandler = ((Result) -> Unit)

class Coma private constructor(activity: AppCompatActivity) {
    private val activity = WeakReference<AppCompatActivity>(activity)
    private var resultRequest: ResultRequest? = null
    private var resultHandler: ResultHandler? = null

    companion object {
        fun withActivity(activity: AppCompatActivity) = Coma(activity)
    }

    fun with(code: Int, intent: Intent): Coma {
        resultRequest = ResultRequest(code, intent)
        return this
    }

    fun withRequestHandler(handler: ResultHandler): Coma {
        resultHandler = handler
        return this
    }

    fun startForResult() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            initializeFragmentAndRequest()
        } else {
            activity.get()?.runOnUiThread { initializeFragmentAndRequest() }
        }
    }

    private fun initializeFragmentAndRequest() {
        var fragment: ComaFragment? =
            activity.get()?.supportFragmentManager
                ?.findFragmentByTag("ComaFragment") as? ComaFragment
        fragment = fragment ?: run {
            val fragmentManager = activity.get()!!.supportFragmentManager
            val frag = ComaFragment().apply { retainInstance = true }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                fragmentManager.beginTransaction().add(frag, "ComaFragment").commit()
                fragmentManager.executePendingTransactions()
            } else
                fragmentManager.beginTransaction().add(frag, "ComaFragment").commitNow()
            return@run frag
        }

        val request = resultRequest ?: throw IllegalArgumentException("ResultRequest must be present!")
        val handler = resultHandler ?: throw IllegalArgumentException("ResultHandler must be present!")
        fragment.startForResult(request, handler)
    }
}