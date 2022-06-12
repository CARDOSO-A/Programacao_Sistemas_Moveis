package com.uniftec.mapas;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utils {
    public PessoaObj getInformacao(String end){
        String json;
        PessoaObj retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    private PessoaObj parseJson(String json){
        try {
            PessoaObj pessoa = new PessoaObj();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");
            JSONObject objArray = array.getJSONObject(0);

            JSONObject obj = objArray.getJSONObject("location");
            JSONObject coordinates = obj.getJSONObject("coordinates");

            pessoa.setLatitude1(coordinates.getString("latitude"));
            pessoa.setLongitude1(coordinates.getString("longitude"));

            return pessoa;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}

