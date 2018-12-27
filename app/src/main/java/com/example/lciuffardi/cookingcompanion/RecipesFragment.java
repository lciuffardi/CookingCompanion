package com.example.lciuffardi.cookingcompanion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.lciuffardi.cookingcompanion.RecipesSearchActivity;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String param1, String param2) {
        RecipesFragment fragment = new RecipesFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipes_layout, container, false);

        if (mListener != null){
            mListener.onFragmentInteraction("Recipes");
        }

        Button searchDessertsAction = (Button) view.findViewById(R.id
                .desserts_button);

        Button searchMeatAction = (Button) view.findViewById(R.id
                .meat_button);

        Button searchPastaAction = (Button) view.findViewById(R.id
                .pasta_button);

        Button searchSaladAction = (Button) view.findViewById(R.id
                .salad_button);

        Button searchSoupAction = (Button) view.findViewById(R.id
                .soup_button);

        Button searchVegetableAction = (Button) view.findViewById(R.id
                .vegetable_button);

        searchDessertsAction.setOnClickListener(this);
        searchMeatAction.setOnClickListener(this);
        searchPastaAction.setOnClickListener(this);
        searchSaladAction.setOnClickListener(this);
        searchSoupAction.setOnClickListener(this);
        searchVegetableAction.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String title) {
        if (mListener != null) {
            mListener.onFragmentInteraction(title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void onClick(final View v) { //check for what button is
        String selection = null;
        switch (v.getId()) {
            case R.id.desserts_button:
                selection = "Desserts";
                break;
            case R.id.meat_button:
                selection = "Meat";
                break;
            case R.id.pasta_button:
                selection = "Pasta";
                break;
            case R.id.salad_button:
                selection = "Salad";
                break;
            case R.id.soup_button:
                selection = "Soup";
                break;
            case R.id.vegetable_button:
                selection = "Vegetables";
                break;
        }
        Intent intent = new Intent(
                getActivity().getApplicationContext(),
                RecipesSearchActivity.class);

        intent.putExtra("selection", selection);
        startActivity(intent);

    }
    private void randomRecipe(int num){
        String type = null;
        String name = null;
        String url = null;

        switch(num){
            case 1:
                name = "Chocolate Chip Skillet Cookie";
                url = "https://life-in-the-lofthouse.com/chocolate-chip-skillet-cookie/";
                type = "Dessert";
                break;
            case 2:
                name = "Easy Churro Bites";
                url = "https://life-in-the-lofthouse.com/easy-churro-bites/";
                type = "Dessert";
                break;
            case 3:
                name = "Heavenly Oreo Dessert";
                url = "https://life-in-the-lofthouse.com/heavenly-oreo-dessert/";
                type = "Dessert";
                break;
            case 4:
                name = "Red Velvet Trifle";
                url = "https://life-in-the-lofthouse.com/red-velvet-trifle/";
                type = "Dessert";
                break;
            case 5:
                name = "Baked Parmesan Pork Chops";
                url = "https://life-in-the-lofthouse.com/baked-parmesan-pork-chops/";
                type = "Meat";
                break;
            case 6:
                name = "Cream Cheese Lasagna";
                url = "https://life-in-the-lofthouse.com/cream-cheese-lasagna/";
                type = "Meat";
                break;
            case 7:
                name = "Slow Cooker BBQ Beef Brisket";
                url = "https://life-in-the-lofthouse.com/slow-cooker-bbq-beef-brisket/";
                type = "Meat";
                break;
            case 8:
                name = "Stuffed French Bread";
                url = "https://life-in-the-lofthouse.com/stuffed-french-bread/";
                type = "Meat";
                break;
            case 9:
                name = "Sweet Pork Nachos";
                url = "https://life-in-the-lofthouse.com/sweet-pork-nachos/";
                type = "Meat";
                break;
            case 10:
                name = "Cazini With Lamb Ragu";
                url = "https://www.saveur.com/cazini-with-lamb-ragu-recipe";
                type = "Pasta";
                break;
            case 11:
                name = "Skinny Fettuccine Alfredo";
                url = "https://life-in-the-lofthouse.com/skinny-fettuccine-alfredo/";
                type = "Pasta";
                break;
            case 12:
                name = "Japanese Tea Leaf Salad";
                url = "https://www.saveur.com/japanese-green-tea-leaf-salad-recipe";
                type = "Salad";
                break;
            case 13:
                name = "Spicy Cucumber Salad";
                url = "https://www.saveur.com/spicy-smashed-cucumber-salad-recipe/";
                type = "Salad";
                break;
            case 14:
                name = "Tex-Mex Chicken Chopped Salad";
                url = "https://life-in-the-lofthouse.com/tex-mex-chicken-chopped-salad/";
                type = "Salad";
                break;
            case 15:
                name = "Crock Pot Chicken Corn Chowder";
                url = "https://life-in-the-lofthouse.com/crock-pot-chicken-corn-chowder/";
                type = "Soup";
                break;
            case 16:
                name = "Maryland Yin Yang Crab Soup";
                url = "https://www.saveur.com/maryland-crab-yin-yang-soup-recipe";
                type = "Soup";
                break;
            case 17:
                name = "Turtle Soup";
                url = "https://www.saveur.com/turtle-soup-southern-recipe";
                type = "Soup";
                break;
            case 18:
                name = "Garlic Parmesan Mashed Potatoes and Gravy";
                url = "https://life-in-the-lofthouse.com/garlic-parmesan-mashed-potatoes-and-gravy/";
                type = "Vegetable";
                break;
            case 19:
                name = "Garlic Roasted Carrots";
                url = "https://life-in-the-lofthouse.com/garlic-roasted-carrots/";
                type = "Vegetable";
                break;
        }
        Intent intent = new Intent(
                getActivity().getApplicationContext(),
                RecipeDetails.class);
        intent.putExtra("type", type);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}