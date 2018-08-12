package com.cocktail.alumis.cocktailpedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;

public class CocktailDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients);
        TextView ingredientsTitleTextView = (TextView) findViewById(R.id.ingredientsTitle);
        TextView directionsTitleTextView = (TextView) findViewById(R.id.directionsTitle);
        TextView directionsTextView = (TextView) findViewById(R.id.directions);
        TextView titleTextView = (TextView) findViewById(R.id.title);


        ingredientsTextView.setText(getIntent().getStringExtra("ingredients"));
        directionsTextView.setText(getIntent().getStringExtra("directions"));
        String cocktailName = getIntent().getStringExtra("cocktailName");
        titleTextView.setText(cocktailName);
        titleTextView.setTypeface(null, BOLD_ITALIC);
        ingredientsTitleTextView.setTypeface(null, BOLD);
        directionsTitleTextView.setTypeface(null, BOLD);


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView_2);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);


        ImageView view = (ImageView) findViewById(R.id.imageView);
        cocktailName = cocktailName.replace(' ','_');
        cocktailName = cocktailName.replace('\'','_');
        String imagePath = "@drawable/" + cocktailName.toLowerCase();
        int imageResource = getResources().getIdentifier(imagePath, null, this.getPackageName());

        view.setImageResource(imageResource);


    }





}
