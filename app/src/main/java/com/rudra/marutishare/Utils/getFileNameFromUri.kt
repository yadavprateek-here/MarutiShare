package com.rudra.marutishare.Utils

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile

@Composable
fun getFileNameFromUri(uri: Uri): String {
    val file = DocumentFile.fromSingleUri(LocalContext.current, uri)
    return if (file?.name == null)
        "no name"
    else
        file.name!!
}