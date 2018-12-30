package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originView;

    private TextView knownAsView;

    private TextView ingredientView;

    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originView = findViewById(R.id.place_of_origin);
        knownAsView = findViewById(R.id.known_as);
        ingredientView = findViewById(R.id.ingredient_list);
        descriptionView = findViewById(R.id.description);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.get()
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        List<String> sandwichList = sandwich.getAlsoKnownAs();
        if (!sandwichList.isEmpty()){
            for (int i = 0; i < sandwichList.size(); i++){
                knownAsView.append("\n" + sandwich.getAlsoKnownAs().get(i));
            }

        }

        List<String> ingredientList = sandwich.getIngredients();
        if (!ingredientList.isEmpty()){
            for (int j = 0; j < ingredientList.size(); j++){
                ingredientView.append("\n" + sandwich.getIngredients().get(j));
            }
        }
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin != null){
            originView.append(" " + placeOfOrigin);
        } else {
            placeOfOrigin = getString(R.string.unknown);
            originView.append(" " + placeOfOrigin);
        }

        descriptionView.append(" " + sandwich.getDescription());

    }
}
