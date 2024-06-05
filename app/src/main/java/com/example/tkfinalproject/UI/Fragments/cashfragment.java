package com.example.tkfinalproject.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LastPage.lastPgae;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.basefragment;

/**
 * The cashfragment class represents a fragment that handles the selection
 * of cash payment method and navigates to the last page with the selected method.
 */
public class cashfragment extends basefragment implements View.OnClickListener {

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Declare button and phone object
    Button btn;
    Phone phone;

    // Parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor for the fragment.
     */
    public cashfragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cashfragment.
     */
    public static cashfragment newInstance(String param1, String param2) {
        cashfragment fragment = new cashfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to do initial creation of a fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cashfragment, container, false);

        // Get phone object from activity's intent
        phone = (Phone) requireActivity().getIntent().getSerializableExtra("price");

        // Initialize button and set click listener
        btn = view.findViewById(R.id.cashbutton);
        btn.setOnClickListener(this);

        return view;
    }

    /**
     * Handles click events for the fragment's button.
     * This method is called when the view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        // Create an intent to navigate to lastPage activity
        Intent intent = new Intent(getActivity(), lastPgae.class);

        // Add payment method and phone object to the intent
        intent.putExtra("method", "מזומן");
        intent.putExtra("phone", phone);

        // Start the lastPage activity
        startActivity(intent);
    }
}
