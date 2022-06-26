package com.example.crudapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AlterarActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public Button buttonAlterar;
    public Integer id;
    public EditText editTextName;
    CheckBox arroz;
    CheckBox leite;
    CheckBox carne;
    CheckBox feijao;
    CheckBox refrigerante;
    String strValorTotal;
    double valorTotal = 0.00;
    public String strVerificar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        buttonAlterar = (Button) findViewById(R.id.buttonAlterar);
        editTextName = (EditText) findViewById(R.id.editTextName);
        arroz = (CheckBox) findViewById(R.id.arroz);
        leite = (CheckBox) findViewById(R.id.leite);
        carne = (CheckBox) findViewById(R.id.carne);
        feijao = (CheckBox) findViewById(R.id.feijao);
        refrigerante = (CheckBox) findViewById(R.id.refrigerante);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        carregarDados();

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });

    }

    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome, arroz, leite, carne, feijao, refrigerante, valorTotal FROM coisa WHERE id = " + id.toString(), null);
            cursor.moveToFirst();
            editTextName.setText(cursor.getString(1));
            strVerificar = cursor.getString(2);
            if(!TextUtils.isEmpty(strVerificar)){
                arroz.setChecked(true);
            }
            strVerificar = cursor.getString(3);
            if(!TextUtils.isEmpty(strVerificar)){
                leite.setChecked(true);
            }
            strVerificar = cursor.getString(4);
            if(!TextUtils.isEmpty(strVerificar)){
                carne.setChecked(true);
            }
            strVerificar = cursor.getString(5);
            if(!TextUtils.isEmpty(strVerificar)){
                feijao.setChecked(true);
            }
            strVerificar = cursor.getString(6);
            if(!TextUtils.isEmpty(strVerificar)){
                refrigerante.setChecked(true);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void alterar() {

        if(!TextUtils.isEmpty(editTextName.getText().toString())){
            if (arroz.isChecked()) {
                valorTotal = valorTotal + 2.69;
            }
            if (leite.isChecked()) {
                valorTotal = valorTotal + 2.70;
            }
            if (carne.isChecked()) {
                valorTotal = valorTotal + 16.70;
            }
            if (feijao.isChecked()) {
                valorTotal = valorTotal + 3.38;
            }
            if (refrigerante.isChecked()) {
                valorTotal = valorTotal + 3.00;
            }
            strValorTotal = Double.toString(valorTotal);

            try {
                bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
                String sql = "UPDATE coisa SET nome=?, arroz=?, leite=?, carne=?, feijao=?, refrigerante=?, valorTotal=? WHERE id=?";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);

                stmt.bindString(1, editTextName.getText().toString());

                if (arroz.isChecked()) {
                    stmt.bindString(2, "Arroz: R$ 2,69" + System.getProperty("line.separator"));
                }
                if (leite.isChecked()) {
                    stmt.bindString(3, "Leite: R$ 2,70" + System.getProperty("line.separator"));
                }
                if (carne.isChecked()) {
                    stmt.bindString(4, "Carne: R$ 16,70" + System.getProperty("line.separator"));
                }
                if (feijao.isChecked()) {
                    stmt.bindString(5, "Feijão: R$ 3,38" + System.getProperty("line.separator"));
                }
                if (refrigerante.isChecked()) {
                    stmt.bindString(6, "Refrigerante: R$ 3,00" + System.getProperty("line.separator"));
                }

                stmt.bindString(7, "Valor Total da Lista: R$ " + strValorTotal);
                stmt.bindLong(8, id);

                stmt.executeUpdateDelete();
                bancoDados.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }
}