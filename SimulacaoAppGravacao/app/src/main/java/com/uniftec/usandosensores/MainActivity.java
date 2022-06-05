package com.uniftec.usandosensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvAproximacao;
    private TextView tvLuminosidade;
    ImageView imagemAtivado;
    ImageView imagemDesativado;
    View contentMain;

    private SensorManager mSensorManager;
    private Sensor mAproximacao;
    private Sensor mLuminosidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemAtivado = findViewById(R.id.imagemAtivado);
        imagemDesativado = findViewById(R.id.imagemDesativado);

        tvAproximacao = (TextView) findViewById( R.id.tvAproximacao );
        tvLuminosidade = (TextView) findViewById( R.id.tvLuminosidade );

        contentMain = (View) findViewById(R.id.contentMain);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            mAproximacao = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            mLuminosidade = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        mSensorManager.registerListener(new SensorAproximacao(), mAproximacao, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(new SensorLuminosidade(), mLuminosidade, SensorManager.SENSOR_DELAY_UI);

    }

    class SensorAproximacao implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float aproximacao = event.values[0];
            tvAproximacao.setText(String.valueOf(aproximacao));

            if(aproximacao > 4){
                imagemAtivado.setVisibility(View.GONE);
                imagemDesativado.setVisibility(View.VISIBLE);
            }else{
                imagemAtivado.setVisibility(View.VISIBLE);
                imagemDesativado.setVisibility(View.GONE);
            }

        }
    }

    class SensorLuminosidade implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float luminosidade = event.values[0];
            tvLuminosidade.setText(String.valueOf(luminosidade));

            if(luminosidade > 6){
                contentMain.setBackgroundColor(getResources().getColor(R.color.colorBranco));
            }else{
                contentMain.setBackgroundColor(getResources().getColor(R.color.colorPreto));
            }
        }
    }
}
