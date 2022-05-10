package com.example.appfragments;

import static com.example.appfragments.R.id.imgFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListFragment extends Fragment {

    private ListView listPlanetas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_list, null );

        listPlanetas = (ListView) view.findViewById( R.id.listAnimais);

        listPlanetas.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                tratarSelecao( position );

            }

        } );

        return view;
    }

    protected void tratarSelecao( int position ) {
        String descricao = listPlanetas.getItemAtPosition( position ).toString();

        Configuration config = getResources().getConfiguration();

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            FragmentManager fragmentManager = getFragmentManager();

            ImgFragment imgFragment = (ImgFragment) fragmentManager.findFragmentById(R.id.imgFragment);

            imgFragment.setConteudo( position, descricao );

        } else {
            ImgFragment fragDir = new ImgFragment();

            Bundle args = new Bundle();
            args.putInt("pos", position);
            args.putString( "descricao", descricao );
            fragDir.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(android.R.id.content, fragDir);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}