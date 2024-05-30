package com.example.tkfinalproject.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LastPage.lastPgae;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.basefragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cashfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cashfragment extends basefragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btn;
    Phone phone;

    private String mParam1;
    private String mParam2;

    public cashfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cashfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cashfragment newInstance(String param1, String param2) {
        cashfragment fragment = new cashfragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cashfragment, container, false);
        phone = (Phone)getActivity().getIntent().getSerializableExtra("price");
        btn = view.findViewById(R.id.cashbutton);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), lastPgae.class);
        intent.putExtra("method","מזומן");
        intent.putExtra("phone",phone);
        startActivity(intent);
    }
}