package com.example.tkfinalproject.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.braintreepayments.cardform.view.CardForm;
import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LastPage.lastPgae;
import com.example.tkfinalproject.Utility.LocaleHelper;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.example.tkfinalproject.Utility.basefragment;

/**
 * The creditCard class represents a fragment that handles credit card payment information.
 * It extends the basefragment and implements View.OnClickListener to manage click events.
 */
public class creditCard extends basefragment implements View.OnClickListener {

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Declare utility class, button, card form, and phone object
    UtilityClass utilityClass;
    Button btn;
    CardForm cardForm;
    Phone phone;

    // Parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor for the fragment.
     */
    public creditCard() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment creditCard.
     */
    public static creditCard newInstance(String param1, String param2) {
        creditCard fragment = new creditCard();
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
        if (savedInstanceState != null) {
            LocaleHelper.setLocale(requireActivity(), "he");
        }
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
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);

        // Get phone object from activity's intent
        phone = (Phone) requireActivity().getIntent().getSerializableExtra("price");

        // Initialize utility class and button, set click listener
        utilityClass = new UtilityClass(getActivity());
        btn = view.findViewById(R.id.aprrovecard);
        btn.setOnClickListener(this);

        // Initialize and set up card form
        cardForm = view.findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .actionLabel("purchase")
                .setup(getActivity());

        // Set hints and gravity for card form fields
        cardForm.getCvvEditText().setFieldHint(R.string.cvvhint);
        EditText cardNumberEditText = cardForm.getCardEditText();
        EditText expirationDateEditText = cardForm.getExpirationDateEditText();
        EditText cvvEditText = cardForm.getCvvEditText();
        EditText cardholderNameEditText = cardForm.getCardholderNameEditText();
        cardNumberEditText.setGravity(Gravity.RIGHT);
        expirationDateEditText.setGravity(Gravity.RIGHT);
        cvvEditText.setGravity(Gravity.RIGHT);
        cardholderNameEditText.setGravity(Gravity.RIGHT);
        cardholderNameEditText.setTextDirection(View.TEXT_DIRECTION_RTL);

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
        if (cardForm.isValid()) {
            // Create an intent to navigate to lastPage activity
            Intent intent = new Intent(getActivity(), lastPgae.class);

            // Add payment method and phone object to the intent
            intent.putExtra("method", "אשראי");
            intent.putExtra("phone", phone);

            // Start the lastPage activity
            startActivity(intent);
        } else {
            // Validate the card form
            cardForm.validate();
        }
    }
}
