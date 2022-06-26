package com.example.crudapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public Button botao;
    public Cursor meuCursor;
    public String lista;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewDados = (ListView) findViewById(R.id.listViewDados);
        botao = (Button) findViewById(R.id.buttonAdicionar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        /*listViewDados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                confirmaExcluir();
                return true;
            }
        });*/

        listViewDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                //abrirTelaAlterar();
                dialogUpdateDelete();
            }
        });

        criarBancoDados();
        //inserirDadosTemp();
        listarDados();
    }

    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS coisa("+"id INTEGER PRIMARY KEY AUTOINCREMENT"+"," +
                    ""+"data DEFAULT CURRENT_TIMESTAMP"+", " +
                    "nome VARCHAR, arroz VARCHAR, leite VARCHAR, carne VARCHAR, feijao VARCHAR, refrigerante VARCHAR, valorTotal VARCHAR)");
            bancoDados.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void listarDados(){
        try {
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            meuCursor = bancoDados.rawQuery("SELECT id, data, nome, arroz, leite, carne, feijao, refrigerante, valorTotal FROM coisa", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while (meuCursor != null){
                arrayIds.add(meuCursor.getInt(0));
                lista = meuCursor.getString(2)+System.getProperty("line.separator");
                lista = lista +"Data: "+ meuCursor.getString(1)+System.getProperty("line.separator")+System.getProperty("line.separator");

                if(meuCursor.getString(3) != null){
                    lista = lista + meuCursor.getString(3);
                }
                if(meuCursor.getString(4) != null){
                    lista = lista + meuCursor.getString(4);
                }
                if(meuCursor.getString(5) != null){
                    lista = lista + meuCursor.getString(5);
                }
                if(meuCursor.getString(6) != null){
                    lista = lista + meuCursor.getString(6);
                }
                if(meuCursor.getString(7) != null){
                    lista = lista + meuCursor.getString(7);
                }

                lista = lista+System.getProperty("line.separator") + meuCursor.getString(8);

                linhas.add(lista);
                meuCursor.moveToNext();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /*public void inserirDadosTemp(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "INSERT INTO coisa (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,"Item 1");
            stmt.executeInsert();
            stmt.bindString(1,"Item 2");
            stmt.executeInsert();
            stmt.bindString(1,"Item 3");
            stmt.executeInsert();

            bancoDados.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }*/

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    /*public void confirmaExcluir() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(MainActivity.this);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Você realmente deseja excluir esse registro?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                excluir();
                listarDados();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        msgBox.show();
    }*/

    public void dialogUpdateDelete(){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(MainActivity.this);
        msgBox.setTitle("Alterar ou Excluir");
        msgBox.setIcon(android.R.drawable.ic_dialog_alert);
        msgBox.setMessage("Escolha uma opção abaixo:");
        msgBox.setNegativeButton("Alterar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirTelaAlterar();
            }
        });
        msgBox.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                excluir();
                listarDados();
            }
        });
        msgBox.show();
    }

    public void excluir(){
        try{
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "DELETE FROM coisa WHERE id =?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, idSelecionado);
            stmt.executeUpdateDelete();
            listarDados();
            bancoDados.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaAlterar(){
        Intent intent = new Intent(this, AlterarActivity.class);
        intent.putExtra("id",idSelecionado);
        startActivity(intent);
    }

}