package com.example.tkfinalproject.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.Utility.basefragment;

/**
 * The cancelFragment class represents a fragment that handles the cancellation
 * of a process and provides a confirmation dialog to the user.
 */
public class cancelFragment extends basefragment implements View.OnClickListener {

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button button;

    // Parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor for the fragment.
     */
    public cancelFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cancelFragment.
     */
    public static cancelFragment newInstance(String param1, String param2) {
        cancelFragment fragment = new cancelFragment();
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
        View view = inflater.inflate(R.layout.fragment_cancel, container, false);

        // Initialize button and set click listener
        button = view.findViewById(R.id.mycancelbutton);
        button.setOnClickListener(this);

        // Adjust size of the view
        super.ajustdsize(requireActivity(), view);

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
        // Show confirmation dialog
        AlertDialog.Builder adb = new AlertDialog.Builder(requireActivity());
        adb.setTitle("ביטול");
        adb.setMessage("האם אתה בטוח שאתה רוצה לבטל את הטרייד-אין ולחזור לדף הבית");
        adb.setCancelable(false);

        // Set positive button with action to start FirstPage activity
        adb.setPositiveButton("כן", (dialog, which) -> {
            Intent intent = new Intent(getActivity(), FirstPage.class);
            startActivity(intent);
        });

        // Set negative button to cancel the dialog
        adb.setNegativeButton("לא", (dialog, which) -> dialog.cancel());

        // Create and show the dialog
        adb.create().show();
    }
}
