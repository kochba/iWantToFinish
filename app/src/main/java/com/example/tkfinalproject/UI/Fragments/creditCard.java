package com.example.tkfinalproject.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.tkfinalproject.R;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.InfoMeassge;
import com.example.tkfinalproject.Utility.LocaleHelper;
import com.example.tkfinalproject.Utility.Phone;
import com.example.tkfinalproject.Utility.UtilityClass;
import com.example.tkfinalproject.Utility.basefragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link creditCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class creditCard extends basefragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    UtilityClass utilityClass;
    Button btn;
    CardForm cardForm;
    Phone phone;
    Bundle mysavedInstanceState;
    int code;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public creditCard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment creditCard.
     */
    // TODO: Rename and change types and number of parameters
    public static creditCard newInstance(String param1, String param2) {
        creditCard fragment = new creditCard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            LocaleHelper.setLocale(getActivity(), "he");
            code = savedInstanceState.getInt("code");
            mysavedInstanceState = savedInstanceState;
        }
        else {
            code = 1;
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);
        phone = (Phone)getActivity().getIntent().getSerializableExtra("price");
        utilityClass = new UtilityClass(getActivity());
        btn = view.findViewById(R.id.aprrovecard);
        btn.setOnClickListener(this);
        cardForm = view.findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .actionLabel("purchase")
                .setup(getActivity());
        addinfo(cardForm);
//        cardForm.getCvvEditText().setHintTextColor(R.color.black);
//        cardForm.getCardEditText().setFieldHint(R.string.numberhint);
//        cardForm.getExpirationDateEditText().setFieldHint(R.string.exphint);
//        cardForm.getCardholderNameEditText().setFieldHint(R.string.namehint);
        cardForm.getCvvEditText().setFieldHint(R.string.cvvhint);
        EditText cardNumberEditText = cardForm.getCardEditText();
        EditText expirationDateEditText = cardForm.getExpirationDateEditText();
        EditText cvvEditText = cardForm.getCvvEditText();
        EditText cardholderNameEditText = cardForm.getCardholderNameEditText();
        cardNumberEditText.setGravity(Gravity.RIGHT);
        expirationDateEditText.setGravity(Gravity.RIGHT);
        cvvEditText.setGravity(Gravity.RIGHT);
        cardholderNameEditText.setGravity(Gravity.RIGHT);
        cardholderNameEditText.setTextDirection(view.TEXT_DIRECTION_RTL);
        super.ajustdsize(requireActivity(),view);
        return view;
    }

    private void addinfo(CardForm cardForm) {
        if (mysavedInstanceState != null){
            if (mysavedInstanceState.getString("name") != null){
                cardForm.getCardholderNameEditText().setText(mysavedInstanceState.getString("name"));
                    Toast.makeText(getActivity(), mysavedInstanceState.getString("name"), Toast.LENGTH_SHORT).show();
            }
            if (mysavedInstanceState.getString("number") != null){
                cardForm.getCardEditText().setText(mysavedInstanceState.getString("number"));
            }
            if (mysavedInstanceState.getString("cvv") != null){
                cardForm.getCvvEditText().setText(mysavedInstanceState.getString("cvv"));
            }
            if (mysavedInstanceState.getString("month") != null && mysavedInstanceState.getString("year") != null){
                cardForm.getExpirationDateEditText().setText(mysavedInstanceState.getString("month") + "/" + mysavedInstanceState.getString("year"));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (code == 1){
            outState.putString("name",cardForm.getCardholderName());
            outState.putString("number",cardForm.getCardNumber());
            outState.putString("month",cardForm.getExpirationMonth());
            outState.putString("year",cardForm.getExpirationYear());
            outState.putString("cvv",cardForm.getCvv());
            outState.putInt("code",2);
        }
        if (code == 2){
            outState.putString("name",mysavedInstanceState.getString("name"));
            outState.putString("number",mysavedInstanceState.getString("number"));
            outState.putString("month",mysavedInstanceState.getString("month"));
            outState.putString("year",mysavedInstanceState.getString("year"));
            outState.putString("cvv",mysavedInstanceState.getString("cvv"));
            outState.putInt("code",1);
        }
    }

    @Override
    public void onClick(View view) {
        if (cardForm.isValid()){
            //SmsSender.sendSms(cardForm.getMobileNumber(),new InfoMeassge(phone,"אשראי",getActivity()));
        }
        else {
            cardForm.validate();
        }
    }
}