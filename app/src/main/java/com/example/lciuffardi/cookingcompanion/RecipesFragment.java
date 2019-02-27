package com.example.lciuffardi.cookingcompanion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Luigi Ciuffardi on 10/1/2017.
 * Last updated by Luigi Ciuffardi on 2/26/2019.
 */
public class RecipesFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecipesFragment() {
        // Required empty public constructor
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

    public interface OnFragmentInteractionListener {
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
}