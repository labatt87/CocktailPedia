package com.cocktail.alumis.cocktailpedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;

    private ListView lv;
    private ArrayAdapter<String> adapter;
    private int countOfRecipes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        lv = (ListView) findViewById(R.id.CocktailList);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9062146672441347/4888342670");

        try {
            InputStream is = getAssets().open("cocktails.txt");


            BufferedReader br = new BufferedReader( new InputStreamReader(is));
            String str;
            final ArrayList<String> cocktailList = new ArrayList<>();
            while ((str = br.readLine()) != null) {
                cocktailList.add(str);
            }


            if ( !cocktailList.isEmpty()){
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cocktailList);
            }

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {

                    countOfRecipes++;
                    String cocktailName = (String) parent.getItemAtPosition(position);
                    if (cocktailName != null && !cocktailName.isEmpty()) {
                        try {
                            InputStream is = getAssets().open(cocktailName + ".txt");

                            BufferedReader br = new BufferedReader( new InputStreamReader(is));

                            String ingredients = "";
                            String directions = "";
                            String txt;
                            while ((txt = br.readLine()) != null && !txt.equals("Directions")){
                                ingredients = ingredients.concat(txt);
                                ingredients = ingredients.concat(System.getProperty("line.separator"));
                            }

                            while ((txt = br.readLine()) != null){
                                directions = directions.concat(txt);
                                directions = directions.concat(System.getProperty("line.separator"));
                            }

                            Intent intent = new Intent(MainActivity.this, CocktailDetailActivity.class);

                            intent.putExtra("ingredients",ingredients);
                            intent.putExtra("directions",directions);
                            intent.putExtra("cocktailName",cocktailName);

                            if (countOfRecipes == 3) {
                                if (!mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    mInterstitialAd.show();
                                } else {
                                    mInterstitialAd.show();
                                }


                                countOfRecipes = 0;
                            }

                            startActivity(intent);


                        }catch (IOException e ){
                            e.printStackTrace();
                        }
                    }

                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
