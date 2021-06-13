package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_getCityId, btn_getWeatherbyID, btn_getWeatherbyName;
    EditText et_dataInput;
    ListView lv_WeatherReport;

    final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_getCityId = findViewById(R.id.btn_getCityId);
        btn_getWeatherbyName = findViewById(R.id.btn_getWeatherByCityName);
        btn_getWeatherbyID = findViewById(R.id.btn_getWeatherByCityId);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_WeatherReport = findViewById(R.id.lv_weatherReport);

        // Click listeners for every button

        btn_getWeatherbyID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //This didn't return shit
                weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                  @Override
                  public void onError(String message) {
                      Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onResponse(String cityID) {
                    Toast.makeText(MainActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
                  }
              });
            }
        });

        btn_getWeatherbyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weatherDataService.getCityForecastByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        // put the entire list into the listview control

                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_WeatherReport.setAdapter(arrayAdapter);
                    }
                });

            }
        });


        btn_getCityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weatherDataService.getCityForecastByID(et_dataInput.getText().toString(), new WeatherDataService.ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        // put the entire list into the listview control

                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_WeatherReport.setAdapter(arrayAdapter);
                    }
                });


//                weatherDataService.getCityForecastByID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String cityID) {
//                        Toast.makeText(MainActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
}