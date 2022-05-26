package com.uniftec.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent telaResultado = getIntent();

        String nome = telaResultado.getStringExtra("nome");
        TextView textViewNome = (TextView) findViewById(R.id.textViewNome);
        textViewNome.setText("Nome: "+nome);

        String email = telaResultado.getStringExtra("email");
        TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewEmail.setText("E-mail: "+email);

        String telefone = telaResultado.getStringExtra("telefone");
        TextView textViewTelefone = (TextView) findViewById(R.id.textViewTelefone);
        textViewTelefone.setText("Telefone: "+telefone);

        Bitmap bitmap = (Bitmap) telaResultado.getParcelableExtra("bitmap");
        ImageView imageView = (ImageView) findViewById(R.id.imagem);
        imageView.setImageBitmap(bitmap);

    }

    public void botaoVoltarOnClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}