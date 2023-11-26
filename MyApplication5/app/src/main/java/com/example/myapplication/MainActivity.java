package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.android.volley.Request.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView stateRecycleview;
    private ArrayList<Model> stateList;
    private Adapter modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateRecycleview = findViewById(R.id.RVstates);
        stateList = new ArrayList<>();
        modelAdapter = new Adapter(stateList, this);
        stateRecycleview.setLayoutManager(new LinearLayoutManager(this));
        stateRecycleview.setAdapter(modelAdapter);
        getData();
    }

    private void getData() {

        String url= "https://data.covid19india.org/state_district_wise.json";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    Iterator<String> keys = response.keys();
                    keys.next();
                    while(keys.hasNext())
                    {
                        String stateName = keys.next();
                        JSONObject jsonObject= response.getJSONObject(stateName);
                        JSONObject districtdata= jsonObject.getJSONObject("districtData");
                        Iterator<String> districts = districtdata.keys();
                        while(districts.hasNext()) {
                            String districtName = districts.next();
                            JSONObject district = districtdata.getJSONObject(districtName);
                            long active = district.getLong("active");
                            long confirmed = district.getLong("confirmed");
                            long deceased = district.getLong("deceased");
                            long recovered = district.getLong("recovered");
                            stateList.add(new Model(stateName,districtName,active,recovered,deceased,confirmed));

                        }
                        modelAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,"Failed to fetch json data!",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"Failed to fetch json data!",Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}