package br.senai.sp.jandira.imcapp20_a.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.utils.calcularNcd

class NcdActivity : AppCompatActivity() {

    lateinit var spinnerIdade: Spinner
    lateinit var spinnerNivelAtividade: Spinner
    lateinit var radioFeminino: RadioButton
    lateinit var radioMasculino: RadioButton
    lateinit var edPeso: EditText
    lateinit var buttonNcd: Button
    lateinit var textViewIdade: TextView
    lateinit var textViewAtividades: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ncd)

        spinnerIdade = findViewById(R.id.spinner_idade)
        spinnerNivelAtividade = findViewById(R.id.spinner_atividades)
        radioFeminino = findViewById(R.id.radio_feminino)
        radioMasculino = findViewById(R.id.radio_masculino)
        edPeso = findViewById(R.id.ed_peso)
        buttonNcd = findViewById(R.id.button_calcular_ncd)
        textViewIdade = findViewById(R.id.text_view_idade)
        textViewAtividades = findViewById(R.id.text_view_atividades)

        buttonNcd.setOnClickListener {

            if (edPeso.text.isEmpty()) {
                edPeso.error = "Peso é obrigatório!"
            } else if(spinnerIdade.selectedItemPosition == 0){
                textViewIdade.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_error_24,
                    0)
            } else if (spinnerNivelAtividade.selectedItemPosition == 0){
                textViewIdade.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0)
                textViewAtividades.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_error_24,
                    0)
            }
            else {
                textViewIdade.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0)
                carregarDados()
            }


        }

    }

    fun carregarDados() {
        var faixaEtaria: Int = 0
        faixaEtaria = spinnerIdade.selectedItemPosition

        var nivelAtividade: Int = 0
        nivelAtividade = spinnerNivelAtividade.selectedItemPosition

        var sexo: Char = 'F'

        if (radioFeminino.isSelected) {
            sexo = 'F'
        } else if (radioMasculino.isSelected){
            sexo = 'M'
        } else {

        }

        var peso: Double = 0.0
        peso = edPeso.text.toString().toDouble()

        val ncd = calcularNcd(
            peso,
            faixaEtaria,
            nivelAtividade,
            sexo
        )
        val intent = Intent(this, ResultadoNcdActivity::class.java)
        intent.putExtra("ncd", ncd)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
    }
}