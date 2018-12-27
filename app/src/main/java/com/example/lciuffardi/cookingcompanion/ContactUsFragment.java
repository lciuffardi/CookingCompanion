package com.example.lciuffardi.cookingcompanion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Luigi Ciuffardi on 10/1/2017.
 * Last updated by Luigi Ciuffardi on 12/27/2018.
 */
public class ContactUsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText emailEntry;
    private EditText messageEntry;
    public static boolean sent = false;

    public ContactUsFragment() {
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
        View view = inflater.inflate(R.layout.contact_us_layout, container, false);

        if (mListener != null){
            mListener.onFragmentInteraction("Contact Us");
        }

        Button email = (Button) view.findViewById(R.id
                .send_button);
        email.setOnClickListener(this);

        emailEntry = (EditText) view.findViewById(R.id.email_editText);
        messageEntry = (EditText) view.findViewById(R.id.user_message_editText);

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
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void onClick(final View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.send_button:
                String email = emailEntry.getText().toString().trim();
                String message = messageEntry.getText().toString().trim();

                Intent intent = new Intent(getActivity(), EmailService.class);
                intent.putExtra("email", email);
                intent.putExtra("message", message);
                getActivity().startService(intent);
        }
    }
}
