package com.example.questapi_236.uicontroller.route

import com.example.questapi_236.R

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}