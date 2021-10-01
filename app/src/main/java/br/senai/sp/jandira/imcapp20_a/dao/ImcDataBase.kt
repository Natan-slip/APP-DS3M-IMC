package br.senai.sp.jandira.imcapp20_a.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

const val DATABASE_NAME = "imc.db"
const val DATABASE_VERSION = 3

class ImcDataBase(context: Context) :
    ManagedSQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        fun getDatabase(context: Context) : ImcDataBase {
            return ImcDataBase(context)
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        Log.i("XPTO", "Abrir onCreate")

        criarTabelaUsuario(db)
        criarTabelaBiometria(db)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE tb_usuario")
        db!!.execSQL("DROP TABLE tb_biometria")
        onCreate(db)
    }

    fun criarTabelaUsuario(db: SQLiteDatabase) {
        val criarTabelaUsuario = "CREATE TABLE tb_usuario (" +
                "id INTEGER PRIMARY KEY," +
                "email TEXT NOT NULL," +
                "senha TEXT NOT NULL," +
                "nome TEXT NOT NULL," +
                "profissao TEXT," +
                "data_nascimento TEXT," +
                "altura REAL NOT NULL," +
                "sexo TEXT NOT NULL," +
                "foto BLOB)"

        db.execSQL(criarTabelaUsuario)
    }

    fun criarTabelaBiometria(db: SQLiteDatabase) {

        val criarTabelaBiometria = "CREATE TABLE tb_biometria (" +
                "id INTEGER PRIMARY KEY," +
                "peso REAL NOT NULL," +
                "nivel_atividade INTEGER NOT NULL," +
                "data_pesagem TEXT NOT NULL," +
                "id_usuario INTEGER NOT NULL)"

        db.execSQL(criarTabelaBiometria)

    }
}