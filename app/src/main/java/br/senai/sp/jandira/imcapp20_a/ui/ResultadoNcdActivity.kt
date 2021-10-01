package br.senai.sp.jandira.imcapp20_a.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.utils.getDicaDoDiaNcd

class ResultadoNcdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_ncd)

        val textViewNcd: TextView = findViewById(R.id.text_view_ncd)
        val textViewDica: TextView = findViewById(R.id.text_view_dica)

        val ncd = intent.getDoubleExtra("ncd", 0.0)

        textViewNcd.text = ncd.toInt().toString()
        textViewDica.text =
            getDicaDoDiaNcd()

    }
}