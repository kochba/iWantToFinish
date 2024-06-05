package com.example.tkfinalproject.UI.SelectPhone;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.Utility.CsvReader;
import com.example.tkfinalproject.Utility.Phone;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

/**
 * The selectNewPhoneMoudle class is responsible for handling the logic related to selecting a new phone.
 */
public class selectNewPhoneMoudle {
    /** Repository instance for data access */
    Repostry repostry;

    /** CSV reader instance for reading phone data */
    CsvReader csvReader;

    /** Random number generator */
    Random random;

    /** Context of the application/activity */
    Context myContext;

    /** TextInputLayout for phone model and capacity inputs */
    com.google.android.material.textfield.TextInputLayout inputModel, inputcapacity;

    /** EditText for displaying maximum price */
    EditText editText;

    /** AutoCompleteTextViews for brand, model, and capacity */
    private final AutoCompleteTextView autoCompleteBrand;
    private final AutoCompleteTextView autoCompleteModel;
    private final AutoCompleteTextView autoCompleteCapacity;

    /** Button for confirming phone selection */
    Button btn;

    /**
     * Constructs a new selectNewPhoneMoudle instance.
     *
     * @param context           The context of the application/activity.
     * @param inputModel        TextInputLayout for phone model input.
     * @param inputcapacity     TextInputLayout for phone capacity input.
     * @param autoCompleteBrand AutoCompleteTextView for brand selection.
     * @param autoCompleteModel AutoCompleteTextView for model selection.
     * @param autoCompleteCapacity AutoCompleteTextView for capacity selection.
     * @param btn               Button for confirming phone selection.
     * @param editText          EditText for displaying maximum price.
     */
    public selectNewPhoneMoudle(Context context, TextInputLayout inputModel, TextInputLayout inputcapacity, AutoCompleteTextView autoCompleteBrand, AutoCompleteTextView autoCompleteModel, AutoCompleteTextView autoCompleteCapacity, Button btn, EditText editText) {
        this.myContext = context;
        this.repostry = new Repostry(context);
        this.inputModel = inputModel;
        this.inputcapacity = inputcapacity;
        this.autoCompleteBrand = autoCompleteBrand;
        this.autoCompleteModel = autoCompleteModel;
        this.autoCompleteCapacity = autoCompleteCapacity;
        this.btn = btn;
        this.editText = editText;
        random = new Random();
        csvReader = new CsvReader(context);
    }

    /**
     * Handles text changes in the brand, model, and capacity fields.
     *
     * @param oldText The old text value.
     * @param newText The new text value.
     * @param code    The code indicating the field being changed (1 for brand, 2 for model, 3 for capacity).
     */
    public void handleModelTextChanged(String oldText, String newText, int code) {
        if (newText != null && !oldText.equals(newText)) {
            if (code == 1) {
                autoCompleteModel.setText("");
                autoCompleteCapacity.setText("");
                editText.setText("");
                inputModel.setEnabled(false);
                inputcapacity.setEnabled(false);
            } else if (code == 2) {
                autoCompleteCapacity.setText("");
                editText.setText("");
                inputcapacity.setEnabled(false);
            } else {
                editText.setText("");
                btn.setEnabled(false);
            }
        }
    }

    /**
     * Creates a Phone object based on the selected phone model, brand, and capacity.
     *
     * @return The Phone object representing the selected phone.
     */
    public Phone cratephoneobj() {
        int rnd = random.nextInt(101) + 1;
        String chooesdPhone = autoCompleteBrand.getText().toString() + " " + autoCompleteModel.getText().toString() + " " + autoCompleteCapacity.getText().toString();
        if (rnd <= 15) {
            return new Phone(1, csvReader.getpriceByCode(myContext, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString(), 4), chooesdPhone, "לא תקין");
        } else if (rnd <= 45) {
            return new Phone(2, csvReader.getpriceByCode(myContext, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString(), 3), chooesdPhone, "מסך קדמי/אחורי שבור");
        } else if (rnd <= 80) {
            return new Phone(3, csvReader.getpriceByCode(myContext, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString(), 2), chooesdPhone, "תקין");
        } else {
            return new Phone(4, csvReader.getpriceByCode(myContext, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString(), 1), chooesdPhone, "תקין+");
        }
    }
}

