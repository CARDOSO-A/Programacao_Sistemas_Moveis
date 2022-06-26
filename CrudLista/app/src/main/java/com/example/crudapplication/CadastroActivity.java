package com.example.crudapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class CadastroActivity extends AppCompatActivity {

    EditText editTextName;
    CheckBox arroz;
    CheckBox leite;
    CheckBox carne;
    CheckBox feijao;
    CheckBox refrigerante;
    Button botao;
    String strValorTotal;
    double valorTotal = 0.00;

    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextName = (EditText) findViewById(R.id.editTextName);
        arroz = (CheckBox) findViewById(R.id.arroz);
        leite = (CheckBox) findViewById(R.id.leite);
        carne = (CheckBox) findViewById(R.id.carne);
        feijao = (CheckBox) findViewById(R.id.feijao);
        refrigerante = (CheckBox) findViewById(R.id.refrigerante);
        botao = (Button) findViewById(R.id.buttonSalvarLista);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){

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
                String sql = "INSERT INTO coisa (nome, arroz, leite, carne, feijao, refrigerante, valorTotal) VALUES (?,?,?,?,?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1,editTextName.getText().toString());
                if(arroz.isChecked()){
                    stmt.bindString(2,"Arroz: R$ 2,69"+System.getProperty("line.separator"));
                }
                if(leite.isChecked()){
                    stmt.bindString(3,"Leite: R$ 2,70"+System.getProperty("line.separator"));
                }
                if(carne.isChecked()){
                    stmt.bindString(4,"Carne: R$ 16,70"+System.getProperty("line.separator"));
                }
                if(feijao.isChecked()){
                    stmt.bindString(5,"Feijão: R$ 3,38"+System.getProperty("line.separator"));
                }
                if(refrigerante.isChecked()){
                    stmt.bindString(6,"Refrigerante: R$ 3,00"+System.getProperty("line.separator"));
                }
                stmt.bindString(7, "Valor Total da Lista: R$ "+strValorTotal);
                stmt.executeInsert();
                bancoDados.close();
                finish();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}