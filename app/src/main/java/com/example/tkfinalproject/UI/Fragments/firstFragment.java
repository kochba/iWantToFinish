package com.example.tkfinalproject.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.basefragment;

/**
 * The firstFragment class represents a fragment that displays and handles
 * information about a phone's model and status. It extends the basefragment.
 */
public class firstFragment extends basefragment {

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Declare UI elements and other variables
    EditText editText, editText2;
    Intent intent;
    Phone phone;

    // Parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor for the fragment.
     */
    public firstFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firstFragment.
     */
    public static firstFragment newInstance(String param1, String param2) {
        firstFragment fragment = new firstFragment();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Initialize UI elements
        editText = view.findViewById(R.id.model);
        editText2 = view.findViewById(R.id.level);

        try {
            // Get phone object from activity's intent
            phone = (Phone) requireActivity().getIntent().getSerializableExtra("price");

            // Set phone details to EditText views
            assert phone != null;
            editText.setText(phone.getCurrentPhone());
            editText2.setText(phone.getStauts());

        } catch (Exception e) {
            // Handle exception by navigating to FirstPage
            intent = new Intent(getActivity(), FirstPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        // Adjust size of the view
        super.ajustdsize(requireActivity(), view);

        return view;
    }
}
