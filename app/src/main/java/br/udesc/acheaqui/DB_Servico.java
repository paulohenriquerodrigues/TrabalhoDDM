package br.udesc.acheaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Servico extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String nome = "teste.db";


    public DB_Servico(Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Servicos (nome TEXT PRIMARY KEY, categoria TEXT, telefone TEXT, valor REAL, descricao TEXT);";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int antigaVersao, int novaVersao) {
        db.execSQL("DROP TABLE IF EXISTS Servicos;");
        onCreate(db);
    }

    public long addServico(String nome, String categoria, String telefone, Float valor, String descricao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("categoria", categoria);
        cv.put("telefone", telefone);
        cv.put("valor", valor);
        cv.put("descricao", descricao);
        long result = db.insert("Servicos", null, cv);
        return result;
    }

}
