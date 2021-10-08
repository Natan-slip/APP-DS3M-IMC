package br.senai.sp.jandira.imcapp20_a.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.graphics.get
import java.io.ByteArrayOutputStream

fun converterBitmapParaByteArray(imagem: Bitmap?): ByteArray? {

    val stream = ByteArrayOutputStream()

    if (imagem != null){
        val imageArray = imagem.compress(
            Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    return null
}

fun converterByteArrayParaBitmap(imageArray: ByteArray) : Bitmap {

    return BitmapFactory.decodeByteArray(
        imageArray, 0, imageArray.size
    )

}

fun converterBitmapParaBase64(bitmap: Bitmap) : String{

    val imageArray = converterBitmapParaByteArray(bitmap)

    return Base64.encodeToString(imageArray, Base64.DEFAULT)
}

fun converterBase64ParaBitmap(image64: String?) : Bitmap {
    var imageArray = Base64.decode(image64, Base64.DEFAULT)
    return converterByteArrayParaBitmap(imageArray)
}