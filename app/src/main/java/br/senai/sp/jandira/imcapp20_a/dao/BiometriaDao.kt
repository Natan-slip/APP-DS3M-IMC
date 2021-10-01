package br.senai.sp.jandira.imcapp20_a.dao

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.model.Biometria

class BiometriaDao(
    val context: Context,
    val biometria: Biometria?) {

    val dbHelper = ImcDataBase.getDatabase(context)

    fun gravar() {

        val db = dbHelper.writableDatabase

        // Determinar os dados que serão inseridos
        val dados = ContentValues()
        dados.put("peso", biometria!!.peso)
        dados.put("nivel_atividade", biometria.nivelAtiviade)
        dados.put("data_pesagem", biometria.dataPesagem)
        dados.put("id_usuario", biometria.usuario)

        // Inserir os dados
        db.insert("tb_biometria", null, dados)

        // Atualizar o SharedPreferences
        val dadosSharedPreferences = context.getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
        val editor = dadosSharedPreferences.edit()
        editor.putInt("peso", biometria!!.peso.toInt())
        editor.commit()

        db.close()
        Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()

    }

    fun getBiometriaById(id: Int) : Biometria? {

        val db = dbHelper.readableDatabase

        // Determinar campos resultantes na busca
        val campos = arrayOf(
            "id",
            "peso",
            "nivel_atividade",
            "data_pesagem",
            "id_usuario"
        )

        // Determinar o filtro
        val filtro = "id_usuario = ?"

        // Determinar o valor do argumento do filtro
        val argumentos = arrayOf(id.toString())

        // Executar a query e guardar o resultado em um cursor
        val cursor = db.query(
            "tb_biometria",
            campos,
            filtro,
            argumentos,
            null,
            null,
            null
        )

        if (cursor.count > 0) {

            cursor.moveToLast()

            val idIndex = cursor.getColumnIndex("id")
            val pesoIndex = cursor.getColumnIndex("peso")
            val nivelAtividadeIndex = cursor.getColumnIndex("nivel_atividade")
            val dataPesagemIndex = cursor.getColumnIndex("data_pesagem")

            val bio = Biometria(
                id = cursor.getInt(idIndex),
                peso = cursor.getDouble(pesoIndex),
                nivelAtiviade = cursor.getInt(nivelAtividadeIndex),
                dataPesagem = cursor.getString(dataPesagemIndex),
                usuario = id
            )

            db.close()

            return bio
        }

        db.close()

        return null
    }


}