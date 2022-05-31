package com.learningandroid.quantumit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.learningandroid.quantumit.Adapters.RecyclerViewAdapter;
import com.learningandroid.quantumit.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListNewsActivity extends AppCompatActivity {

    private static final String APIKEY ="edeb71fcaf554a6285a140fa74ce34f3" ;
    private RecyclerView recyclerView;
    private RequestQueue queue;
    private List<News> newsList;
    private List<News> newsList2;
    public static final String TAG = "check";
    private EditText searchBar;
    private RecyclerViewAdapter adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        recyclerView = findViewById(R.id.news_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsList2 = new ArrayList<>();

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.getCache().clear();

        context = this;

        String url = "https://newsapi.org/v2/everything?q=keyword&apiKey=edeb71fcaf554a6285a140fa74ce34f3";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            if(response != null) {
                try {
                    Toast.makeText(this, "success - " + response.getString("totalResults"), Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray = response.getJSONArray("articles");
                    int size = jsonArray.length();
                    size = Math.min(20,size);
                    for(int i=0 ; i<size ; i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONObject source = obj.getJSONObject("source");

                        News addNews = new News(obj.getString("title"),
                                obj.getString("description"),
                                source.getString("name"),
                                obj.getString("url"),
                                obj.getString("urlToImage"),
                                obj.getString("publishedAt"),
                                obj.getString("content"));

                        newsList.add(addNews);
                    }

                    adapter = new RecyclerViewAdapter(newsList, this);
                    recyclerView.setAdapter(adapter);
                    searchBar = findViewById(R.id.searchbar);
                    searchBar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            newsList2.clear();
                            String typeText = editable.toString().toLowerCase().trim();
                            for(News item : newsList) {
                                if(item.getTitle().toLowerCase().contains(typeText)) {
                                    newsList2.add(item);
                                }
                            }
                            adapter = new RecyclerViewAdapter(newsList2, context);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
                catch (JSONException e) {
                    Log.d(TAG, "request error - " + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error is ", error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "edeb71fcaf554a6285a140fa74ce34f3");
                headers.put("User-Agent" , "Mozilla/5.0");
                return headers;
            }


//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("q", "keyword");
//                params.put("apiKey","edeb71fcaf554a6285a140fa74ce34f3");
//                return params;
//            }
        }

        ;

        queue.add(jsonObjectRequest);

    }
}