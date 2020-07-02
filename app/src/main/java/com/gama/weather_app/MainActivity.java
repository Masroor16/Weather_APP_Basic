package com.gama.weather_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gama.weather_app.Retrofit.ApiClient;
import com.gama.weather_app.Retrofit.ApiInterface;
import com.gama.weather_app.Retrofit.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    ImageView search;
    TextView tempText,city,humidityText,windText,pressureText,minTempText,maxTempText,descriptionText;
    EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.searchImg);
        tempText = findViewById(R.id.temp);
        maxTempText = findViewById(R.id.temp_max);
        minTempText = findViewById(R.id.temp_min);
        windText = findViewById(R.id.wind);
        pressureText = findViewById(R.id.pressure);
        humidityText = findViewById(R.id.humidity);
        textField = findViewById(R.id.city);
        descriptionText = findViewById(R.id.status);
        city = findViewById(R.id.cityText);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Here we will call API
                getWeatherData(textField.getText().toString().trim());
                Log.e("city",textField.getText().toString());

            }
        });

    }

    private void getWeatherData(final String name){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<Example> call = apiInterface.getWeatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call,@NonNull Response<Example> response) {

                tempText.setText((response.body().getMain().getTemp())+"°C");
                minTempText.setText("Min Temp: "+response.body().getMain().getTempMin()+"°");
                maxTempText.setText("Max Temp: "+response.body().getMain().getTempMax()+"°");
                pressureText.setText(response.body().getMain().getPressure());
                humidityText.setText(response.body().getMain().getHumidity());
                descriptionText.setText("Feels Like: "+response.body().getMain().getFeelsLike()+"°C");
                windText.setText(response.body().getWind().getSpeed()+" Km/h");
                city.setText(name);

            }

            @Override
            public void onFailure(@NonNull Call<Example> call,@NonNull Throwable t) {

            }
        });


    }
}