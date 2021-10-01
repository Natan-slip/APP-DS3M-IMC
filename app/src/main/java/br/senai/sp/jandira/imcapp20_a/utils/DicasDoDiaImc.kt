package br.senai.sp.jandira.imcapp20_a.utils

import android.util.Log
import java.util.*

fun getDicaDoDiaImc() : String {

    val dicas = listOf<String>(
        "Reduza alimentos com gordura, açúcar e sal. Tenha sempre frutas, verduras e legumes na alimentação diária.",
        "Inicie uma atividade física. Evite escadas rolantes e elevadores e suba a pé.",
        "Não espere ter sede para beber água. Quando você tem sede, significa que o corpo já está desidratado.",
        "Crie um ritual, deixe o ambiente escuro e silencioso. Quem dorme pouco e tem uma qualidade de sono ruim, tem mais chances de aumentar o peso."
    )

    val sorteio = Random()
    val dica = sorteio.nextInt(dicas.size)

    return dicas[dica]

}