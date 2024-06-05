package com.example.tkfinalproject.Utility;

import android.content.Context;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * CsvReader is a class that reads data from a CSV file and provides various methods
 * to retrieve distinct brands, models by brand, capacities by brand and model,
 * and prices based on the brand, model, and capacity.
 */
public class CsvReader {
    Context mycontext;
    UtilityClass utilityClass;

    /**
     * Constructs a new CsvReader with the specified context.
     *
     * @param mycontext the context to use for accessing assets.
     */
    public CsvReader(Context mycontext) {
        this.mycontext = mycontext;
        utilityClass = new UtilityClass(mycontext);
    }

    /**
     * Retrieves a list of distinct brands from the CSV file.
     *
     * @param context the context to use for accessing assets.
     * @return a list of distinct brands.
     */
    public List<String> getDistinctBrands(Context context) {
        Set<String> brandsSet = new HashSet<>();

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("tradeinprice.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;

            // Skip the header row
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 3) {
                    String brand = nextLine[0].trim();
                    brandsSet.add(brand);
                }
            }

            reader.close();
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }

        return new ArrayList<>(brandsSet);
    }

    /**
     * Retrieves a list of models for the specified brand from the CSV file.
     *
     * @param context the context to use for accessing assets.
     * @param selectedBrand the brand for which to retrieve models.
     * @return a list of models for the specified brand.
     */
    public List<String> getModelsByBrand(Context context, String selectedBrand) {
        LinkedHashSet<String> modelsList = new LinkedHashSet<>();

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("tradeinprice.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;

            // Skip the header row
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 3) {
                    String brand = nextLine[0].trim();
                    String model = nextLine[1].trim();

                    if (brand.equalsIgnoreCase(selectedBrand)) {
                        modelsList.add(model);
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }

        return new ArrayList<>(modelsList);
    }

    /**
     * Retrieves a list of capacities for the specified brand and model from the CSV file.
     *
     * @param context the context to use for accessing assets.
     * @param selectedBrand the brand for which to retrieve capacities.
     * @param selectedModel the model for which to retrieve capacities.
     * @return a list of capacities for the specified brand and model.
     */
    public List<String> getCapcity(Context context, String selectedBrand, String selectedModel) {
        List<String> capcitylist = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("tradeinprice.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;

            // Skip the header row
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 3) {
                    String brand = nextLine[0].trim();
                    String model = nextLine[1].trim();
                    String capicty = nextLine[2].trim();

                    if (brand.equalsIgnoreCase(selectedBrand) && model.equalsIgnoreCase(selectedModel)) {
                        capcitylist.add(capicty);
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }

        return capcitylist;
    }

    /**
     * Retrieves the price for the specified brand, model, and capacity from the CSV file.
     *
     * @param context the context to use for accessing assets.
     * @param selectedBrand the brand for which to retrieve the price.
     * @param selectedModel the model for which to retrieve the price.
     * @param selectCapcity the capacity for which to retrieve the price.
     * @return the price for the specified brand, model, and capacity, or null if not found.
     */
    public String getprice1(Context context, String selectedBrand, String selectedModel, String selectCapcity) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("tradeinprice.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;

            // Skip the header row
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 3) {
                    String brand = nextLine[0].trim();
                    String model = nextLine[1].trim();
                    String capicty = nextLine[2].trim();
                    String price1 = nextLine[3].trim();

                    if (brand.equalsIgnoreCase(selectedBrand) && model.equalsIgnoreCase(selectedModel) && capicty.equalsIgnoreCase(selectCapcity)) {
                        reader.close();
                        return price1;
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }

        return null;
    }

    /**
     * Retrieves the price by code for the specified brand, model, and capacity from the CSV file.
     *
     * @param context the context to use for accessing assets.
     * @param selectedBrand the brand for which to retrieve the price.
     * @param selectedModel the model for which to retrieve the price.
     * @param selectCapcity the capacity for which to retrieve the price.
     * @param code the code for the specific price to retrieve (1-4).
     * @return the price for the specified brand, model, and capacity based on the code, or null if not found.
     */
    public String getpriceByCode(Context context, String selectedBrand, String selectedModel, String selectCapcity, int code) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("tradeinprice.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;
            String[] prices = new String[4];

            // Skip the header row
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 3) {
                    String brand = nextLine[0].trim();
                    String model = nextLine[1].trim();
                    String capicty = nextLine[2].trim();
                    prices[0] = nextLine[3].trim();
                    prices[1] = nextLine[4].trim();
                    prices[2] = nextLine[5].trim();
                    prices[3] = nextLine[6].trim();

                    if (brand.equalsIgnoreCase(selectedBrand) && model.equalsIgnoreCase(selectedModel) && capicty.equalsIgnoreCase(selectCapcity)) {
                        reader.close();
                        return prices[code - 1];
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            utilityClass.showAlertExp();
        }

        return null;
    }
}
