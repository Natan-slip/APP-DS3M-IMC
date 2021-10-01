package br.senai.sp.jandira.imcapp20_a.model

import android.graphics.Bitmap
import java.time.LocalDate

data class Usuario (
    var id: Int = 0,
    var email: String,
    var senha: String,
    var nome: String,
    var profissao: String,
    var altura: Double,
    var dataNascimento: String,
    var sexo: Char,
    var foto: Bitmap? = null
)