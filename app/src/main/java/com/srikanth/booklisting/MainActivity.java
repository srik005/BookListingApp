package com.srikanth.booklisting;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
    public static final String TITLE = "title";
    public static final String AUTHOR_NAME = "authors";
    String url = "https://www.googleapis.com/books/v1/volumes?q=android";
    ArrayList<HashMap<String, String>> contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact = new ArrayList<>();
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titleName = ((TextView) view.findViewById(R.id.name)).getText().toString();
                String authname = ((TextView) view.findViewById(R.id.authorName)).getText().toString();
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra(TITLE,titleName);
                i.putExtra(AUTHOR_NAME,authname);
                startActivity(i);
            }
        });

        new BookTask().execute();
    }

    private class BookTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String author = null;
            String stream = null;
            HttpData httpData = new HttpData();
            stream = httpData.getData(url);
            if (stream != null) {
                try {
                    JSONObject jsonObject = new JSONObject(stream);
                    String kindStr = jsonObject.getString("kind");
                    String totItem = jsonObject.getString("totalItems");
                    Log.d("Kind", kindStr);
                    Log.d("TOtalitem", totItem);

                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject items = jsonArray.getJSONObject(i);

                        JSONObject volumeInfo = items.getJSONObject("volumeInfo");
                        String bookTitle = volumeInfo.getString(TITLE);
                        Log.d("BookTitle", bookTitle);

                       JSONArray authorName = volumeInfo.getJSONArray(AUTHOR_NAME);
                        for(int j=0;j<authorName.length();j++){
                            author=authorName.getString(0);
                        }
                        HashMap<String, String> cont = new HashMap<>();
                        cont.put(TITLE, bookTitle);
                        cont.put(AUTHOR_NAME,author);
                        contact.add(cont);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView) findViewById(R.id.txtJson);
                        tv.setText(R.string.txt_no_data);
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contact, R.layout.list_item, new String[]{TITLE,AUTHOR_NAME}, new int[]{R.id.name,R.id.authorName});
            setListAdapter(adapter);

        }
    }

}
