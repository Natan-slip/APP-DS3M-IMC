package br.senai.sp.jandira.imcapp20_a.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.UsuarioDao
import br.senai.sp.jandira.imcapp20_a.model.Usuario
import kotlinx.android.synthetic.main.activity_novo_usuario.*
import java.util.*

const val CODE_IMAGE = 100

class NovoUsuarioActivity : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    lateinit var imgProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

        imgProfile = findViewById(R.id.img_profile)


        supportActionBar!!.title = " Novo usuário"
        supportActionBar!!.subtitle = " Cadastre o seus dados"
        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.toolbar))
        supportActionBar!!.elevation = 0.0f
        // Detectar o click no texto "Trocar foto"
        tv_trocar_foto.setOnClickListener {
            abrirGaleria()
        }

        // Criar um calendário
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        // Abrir um componente DatePickerDialog
        et_data_nascimento.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, _ano, _mes, _dia ->
                    var diaZero = "$_dia"
                    var mesZero = "$_mes"

                    if (_dia < 10) {
                        diaZero = "0$_dia"
                    }

                    if (_mes < 9) {
                        mesZero = "0${_mes + 1}"
                    }
                    et_data_nascimento.setText("$diaZero/$mesZero/$_ano")
                }, ano, mes, dia
            )
            dpd.show()
        }
    }



    private fun salvar() {
        val usuario = Usuario(
            0,
            et_email.text.toString(),
            et_senha.text.toString(),
            et_nome.text.toString(),
            et_profissao.text.toString(),
            et_altura.text.toString().toDouble(),
            et_data_nascimento.text.toString(),
            if(radio_feminino.isChecked) 'F' else 'M',
            imageBitmap

        )

        val dao = UsuarioDao(this, usuario)
        dao.gravar()

        Toast.makeText(this, "Dados gravados com sucesso!!", Toast.LENGTH_SHORT).show()

        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_novo_usuario,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.menu_save -> {
                Toast.makeText(this,"Salvar",Toast.LENGTH_LONG).show()
                salvar()
                return true
            }
            R.id.menu_cancel -> {
                Toast.makeText(this,"Cancelar",Toast.LENGTH_LONG).show()
                return true
            }
            R.id.menu_help ->{
                Toast.makeText(this,"Ajuda",Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun abrirGaleria() {

        // Chamando a galeria de imagens
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        // Definir qual o tipo de conteúdo deverá ser obtido
        intent.type = "image/*"

        // Iniciar a Activity, mas neste caso nós queremos que
        // esta Activity retorne algo pra gente, a imagem
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Escolha uma foto"
            ),
            CODE_IMAGE
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMAGE && resultCode == -1) {

            // Recuperar a imagem no stream
            val stream = contentResolver.openInputStream(data!!.data!!)

            // Transformar o stream em um BitMap
            imageBitmap = BitmapFactory.decodeStream(stream)

            // Colocar a imagem no ImageView
            imgProfile.setImageBitmap(imageBitmap)
        }

    }
}