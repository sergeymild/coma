package com.android.coma

import android.content.Intent
import androidx.fragment.app.Fragment

class ComaFragment : Fragment() {
    private var resultRequest: ResultRequest? = null
    private var resultHandler: ResultHandler? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultRequest?.requestCode == requestCode) {
            resultHandler?.invoke(Result(requestCode, resultCode, data))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun startForResult(request: ResultRequest, handler: ResultHandler) {
        resultRequest = request
        resultHandler = handler
        startActivityForResult(request.intent, request.requestCode)
    }
}