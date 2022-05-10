package com.example.appfragments;

import android.app.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImgFragment extends Fragment {

    private int imagens_id[] = {
        R.drawable.terra,
        R.drawable.mercurio,
        R.drawable.saturno,
        R.drawable.venuz,
    };

    private ImageView imgPlanetas;
    private TextView tvDescricao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_img, null );
        imgPlanetas = (ImageView) view.findViewById(R.id.imgPlanetas);
        tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        imgPlanetas.setScaleType(ImageView.ScaleType.FIT_XY);

        Configuration config = getResources().getConfiguration();

        if (config.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            String descricao = getArguments().getString("descricao");
            int position = getArguments().getInt("pos");
            imgPlanetas.setImageResource(imagens_id[position]);
            tvDescricao.setText(descricao);
        }
        return view;
    }

    public void setConteudo( int pos, String descricao ) {
        imgPlanetas.setImageResource( imagens_id[ pos ] );
        tvDescricao.setText( descricao );
    }
}