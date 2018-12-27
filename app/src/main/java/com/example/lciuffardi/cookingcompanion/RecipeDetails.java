package com.example.lciuffardi.cookingcompanion;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Luigi Ciuffardi on 10/1/2017.
 * Last updated by Luigi Ciuffardi on 12/27/2018.
 */

public class RecipeDetails extends AppCompatActivity {
    private String name = null;
    private String url = null;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_data);
        ActionBar actionbar = this.getSupportActionBar();

        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");

        actionbar.setTitle(name);

        webView = (WebView) findViewById(R.id.recipe_WebView);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
         case R.id.action_close:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
