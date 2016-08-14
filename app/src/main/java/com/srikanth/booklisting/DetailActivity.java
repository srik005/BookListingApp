package com.srikanth.booklisting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by SrikanthGovindan on 14-08-2016.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String strTitle = getIntent().getStringExtra(MainActivity.TITLE);
        Log.d("Title", "" + strTitle);
        String strAuthor = getIntent().getStringExtra(MainActivity.AUTHOR_NAME);
        Log.d("Author", "" + strAuthor);
        TextView tv = (TextView) findViewById(R.id.details);
        TextView tvAuthor = (TextView) findViewById(R.id.authName);
        if (tv != null) {
            tv.setText(strTitle);
        }
        if (tvAuthor != null) {
            tvAuthor.setText(strAuthor);
        }
    }
}
