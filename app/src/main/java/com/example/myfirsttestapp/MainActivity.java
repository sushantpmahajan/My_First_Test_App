package com.example.myfirsttestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<VideoInfoModel> customVideoList = new ArrayList<VideoInfoModel>();
    private static CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_OnClick(View view){
        try {
            TextView responseTextView;

            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {

            }

            if (customVideoList.isEmpty() == false){
                customVideoList.clear();
            }

            EditText searchText = (EditText) findViewById(R.id.editText);

            //Some url endpoint that you may have
            String myUrl = "http://www.omdbapi.com/?s=" + Uri.encode(searchText.getText().toString()) + "&apikey=771de52";

            //String to place our result in
            String result;
            //Instantiate new instance of our class
            HttpGetRequest getRequest = new HttpGetRequest();
            //Perform the doInBackground method, passing in our url
            result = getRequest.execute(myUrl).get();

            JSONObject searchJson = new JSONObject(result);

            JSONArray videoJsonArray = searchJson.getJSONArray("Search");

            for (int counter = 0; counter < videoJsonArray.length(); counter++){
                JSONObject currentVideoJsonItem = videoJsonArray.getJSONObject(counter);
                VideoInfoModel currentVideoInfoModel = new VideoInfoModel();
                currentVideoInfoModel.setImdbID(currentVideoJsonItem.getString("imdbID"));
                currentVideoInfoModel.setTitle(currentVideoJsonItem.getString("Title"));
                currentVideoInfoModel.setReleaseYear(currentVideoJsonItem.getString("Year"));
                currentVideoInfoModel.setType(currentVideoJsonItem.getString("Type"));
                currentVideoInfoModel.setPosterUrl(currentVideoJsonItem.getString("Poster"));
                customVideoList.add(currentVideoInfoModel);
            }

            adapter = new CustomAdapter(this, customVideoList, getResources());
            ListView customListView = (ListView) findViewById(R.id.list);
            customListView.setAdapter(adapter);

        } catch (Exception ex) {

        }

    }




}
