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
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.UsuarioDao
import br.senai.sp.jandira.imcapp20_a.model.Usuario
import kotlinx.android.synthetic.main.activity_novo_usuario.*
import org.jetbrains.anko.toast
import java.util.*

const val CODE_IMAGE = 100

class NovoUsuarioActivity : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    lateinit var imgProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

        supportActionBar!!.title = "Novo usuário"
        supportActionBar!!.subtitle = "cadastre os seus dados"
        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.tool_bar_background))

        supportActionBar!!.elevation = 0.0f

        imgProfile = findViewById(R.id.img_profile)

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
            val dpd = DatePickerDialog(this,
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

        bt_gravar.setOnClickListener {
            // *** Criar o sharedPreferences
//            val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
//
//            val editor = dados.edit()
//            editor.putString("nome", et_nome.text.toString())
//            editor.putString("profissao", et_profissao.text.toString())
//            editor.putInt("peso", et_peso.text.toString().toInt())
//            editor.putInt("idade", et_data_nascimento.text.toString().toInt())
//            editor.putString("email", et_email.text.toString())
//            editor.putString("senha", et_senha.text.toString())
//            editor.apply()

            // Gravar o novo usuário no banco de dados SQLite

            salvar()
        }

    }

    //Inflando o menu ao ser carregado
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater;
        inflater.inflate(R.menu.menu_nono_usuario, menu)
        return true;
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
            if (radio_feminino.isChecked) 'F' else 'M',
            imageBitmap
        )

        val dao = UsuarioDao(this, usuario)
        dao.gravar()

        Toast.makeText(this, "Dados gravados com sucesso!!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_save -> {
                salvar()

                Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT)

                return true
            }
            R.id.menu_cancelar -> {
                Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT)
                return true
            }
            R.id.menu_help -> {
                Toast.makeText(this, "Ajudar", Toast.LENGTH_SHORT)
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