package br.senai.sp.jandira.imcapp20_a.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.utils.calcularImc
import br.senai.sp.jandira.imcapp20_a.utils.calcularNcd
import br.senai.sp.jandira.imcapp20_a.utils.converterBase64ParaBitmap
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoardActivity : AppCompatActivity() {

    var id = 0
    var nome = ""
    var profissao = ""
    var peso = 0
    var altura = 0.0
    var idade = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        preencherDashBoard()

        supportActionBar!!.hide()

        tv_logout.setOnClickListener {
            val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
            val editor = dados.edit()
            editor.putBoolean("lembrar", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        card_weight.setOnClickListener {
            atualizarBio()
        }

    }

    private fun atualizarBio() {
        val intent = Intent(this, BiometriaActivity::class.java)
        intent.putExtra("id_usuario", id)
        startActivity(intent)
    }

    private fun criarAlertDialog() {
        val dialog = AlertDialog.Builder(this)

        dialog.setMessage("Você não terminou seu cadastro, deseja terminar ?")
            .setCancelable(false)
            .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                atualizarBio()
            })
            .setNegativeButton("Não", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })

        val alert = dialog.create()

        alert.setTitle("Precisamos de mais informações")

        alert.show()
    }

    private fun preencherDashBoard() {
        val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)

        nome = dados.getString("nome", "")!!
        profissao = dados.getString("profissao", "")!!
        peso = dados.getInt("peso", 0)
        idade = dados.getString("idade", "")!!
        altura = dados.getFloat("altura", 0.0f).toDouble()


        tv_profile_name.text = nome
        tv_profile_occupation.text = profissao
        tv_weight.text = peso.toString()
        tv_age.text = idade

        val imagemBase64 = dados.getString("foto", "")
        val imagemBitmap = converterBase64ParaBitmap(imagemBase64)

        id = dados.getInt("id_usuario", 0)

        iv_profile.setImageBitmap(imagemBitmap)

        if (dados.getInt("peso", 0) == 0) {
            criarAlertDialog()
        }

        tv_imc.text = String.format("%.1f", calcularImc(peso.toDouble(), altura))
        tv_ncd.text = String.format("%.1f", calcularNcd(peso.toDouble(),faixaEtaria = 1,nivelAtividade = 0,sexo = 0.toChar()))

    }

    override fun onResume() {
        super.onResume()
        preencherDashBoard()
    }
}