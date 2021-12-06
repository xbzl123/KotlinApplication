package com.example.kotlinapplication

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.net.Uri

object Picker {

    private const val FILE_TYPE = "*/*"
    private const val REQUEST_CODE = 1121

    fun showPicker(context: Activity) {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        // the type of file we want to pick; for now it's all
        intent.type = FILE_TYPE

        // allows us to pick multiple files
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        context.startActivityForResult(Intent.createChooser(intent, "Select file."), REQUEST_CODE)
    }

    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        onActionComplete: OnActionComplete
    ) {

        if (data == null) return // data is null we cannot do anything

        if (resultCode == RESULT_CANCELED) return // user cancelled the selection

        when (requestCode) {
            REQUEST_CODE -> {

                // user has selected single item
                val uri = data.data
                uri?.let {
                    println("DEBUG: $uri")
                    onActionComplete.onSuccess(uri = arrayListOf(it))
                    return
                }

                // user has selected multiple items
                val clipData = data.clipData
                clipData?.let {
                    val itemCount = it.itemCount
                    val uris = arrayListOf<Uri>()
                    for (index in 0 until itemCount) {
                        val itemUri = it.getItemAt(index).uri
                        println("DEBUG: $itemUri")
                        uris.add(itemUri)
                    }

                    onActionComplete.onSuccess(uri = uris)
                }

            }
        }
    }

    interface OnActionComplete {
        fun onSuccess(uri: List<Uri>)
        fun onFailure()
    }

}