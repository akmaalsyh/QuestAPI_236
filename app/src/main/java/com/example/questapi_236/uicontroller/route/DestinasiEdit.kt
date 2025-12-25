package com.questapi_236.uicontroller.route

import com.example.questapi_236.R
import com.example.questapi_236.uicontroller.route.DestinasiNavigasi

object DestinasiEdit : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}