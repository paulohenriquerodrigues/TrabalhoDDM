package br.udesc.acheaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Usuario extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String nome = "BaseDados_AcheAqui.db";


    public DB_Usuario(Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Usuarios(nome TEXT, email TEXT PRIMARY KEY, senha TEXT, cidade TEXT, bairro TEXT, sexo TEXT);";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int antigaVersao, int novaVersao) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios;");
        onCreate(db);
    }

    public long addUsuario(String nome, String email, String senha, String cidade, String bairro, String sexo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("email", email);
        cv.put("senha", senha);
        cv.put("cidade", cidade);
        cv.put("bairro", bairro);
        cv.put("sexo",sexo);
        long result = db.insert("Usuarios", null,cv);
        return result;
    }


}
