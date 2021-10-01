package br.senai.sp.jandira.imcapp20_a.utils

import android.util.Log
import java.util.*

fun getDicaDoDiaNcd() : String {

    val dicas = listOf<String>(
        "Procure manter a \"dieta\" de segunda à sexta-feira, mas cuidado com os excessos de final de semana. Comer de “tudo”, mas cuidar com as quantidades.",
        "Quando sair para comer fora: Escolher pratos que acompanhem legumes e usar bebida alcoólica com moderação (intercalada com 1 copo de água).",
        "Alimentos que podem apresentar consumo livre: Chá ou café (com adoçante), água de coco, refrigerante diet/light, limonada (com adoçante), gelatina diet/light, suco de melão ou melancia (sem açúcar) e hortaliças.",
        "Comece pela salada: Pessoas que comem saladas antes do almoço consomem 12% menos de calorias do que se fossem direto ao prato principal.",
        "Beba 2 litros de água por dia entre as refeições.",
        "Escolha sempre cores variadas de frutas e verduras, pois cada cor representa um tipo diferente de vitamina e esses pigmentos protegem nosso corpo de inúmeras doenças (vitaminas antioxidantes).",
        "Beba muita água durante o dia, mas não durante as refeições, pois atrapalha a digestão e distende o abdômen."
    )

    val sorteio = Random()
    val dica = sorteio.nextInt(dicas.size)

    return dicas[dica]

}