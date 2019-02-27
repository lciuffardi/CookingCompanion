package com.example.lciuffardi.cookingcompanion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Luigi Ciuffardi on 9/30/2017.
 * Last updated by Luigi Ciuffardi on 2/26/2019.
 */
public class IngredientsFragment extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(String param1, String param2) {
        IngredientsFragment fragment = new IngredientsFragment();
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
        View view = inflater.inflate(R.layout.ingredients_inventory_layout, container, false);

        if (mListener != null){
            mListener.onFragmentInteraction("Inventory");
        }
        Button addIngredientsListLayout = (Button) view.findViewById(R.id
                .add_ingredients_button);
        addIngredientsListLayout.setOnClickListener(this);

        ListView listView = (ListView) view.findViewById(
                R.id.ingredient_inventory_listView);
        IngredientsDatabaseManager dbMgr = new IngredientsDatabaseManager(getActivity()
                .getApplicationContext());

        final Cursor cursor = dbMgr.getIngredientCursor(dbMgr.getReadableDatabase());
        getActivity().startManagingCursor(cursor);

        ListAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.two_line_list_item,
                cursor,
                new String[] {IngredientsDatabaseManager.NAME,
                        IngredientsDatabaseManager.EXPIRATION},
                new int[] {android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER){
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View itemView = super.getView(position, convertView, parent);
                try {
                    cursor.moveToPosition(position);
                    Date today = Calendar.getInstance().getTime();
                    Calendar warning = Calendar.getInstance();
                    warning.setTime(today);
                    warning.add(Calendar.DAY_OF_YEAR, 3);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = cursor.getString(cursor.getColumnIndex(IngredientsDatabaseManager.EXPIRATION));
                    if(!date.isEmpty()) {
                        String[] restructureDate = date.split("/");
                        date = restructureDate[2] + "-" + restructureDate[0] + "-" + restructureDate[1] + " " + "00:00:00";
                        Date expiration = df.parse(date);

                        //Sets background color of ingredient based on how close to expiration it is
                        if (today.after(expiration))
                            itemView.setBackgroundColor(Color.RED);
                        else if (warning.getTime().after(expiration))
                            itemView.setBackgroundColor(Color.YELLOW);
                        else
                            itemView.setBackgroundColor(Color.GREEN);
                    } else
                        itemView.setBackgroundColor(Color.GREEN);
                }
                catch(ParseException ex){
                    ex.printStackTrace();
                }
                return itemView;
            }
        };

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        Intent intent = new Intent(
                                getActivity().getApplicationContext(),
                                IngredientDetail.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });


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
        void onFragmentInteraction(String title);
    }

    public void onClick(final View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.add_ingredients_button:
                startActivity(new Intent(getActivity(),
                        AddIngredientsActivity.class));
                break;
        }
    }
}
