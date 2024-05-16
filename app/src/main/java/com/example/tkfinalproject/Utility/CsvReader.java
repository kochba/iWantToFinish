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

public class CsvReader {
    Context mycontext;
    UtilityClass utilityClass;

    public CsvReader(Context mycontext) {
        this.mycontext = mycontext;
        utilityClass = new UtilityClass(mycontext);
    }

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
        } catch (Exception e)
        {
            utilityClass.showAlertExp();
//            e.printStackTrace();
//            Log.e("CSVDataReader", "Error reading CSV file: " + e.getMessage());
        }

        return new ArrayList<>(brandsSet);
    }

    public List<String> getModelsByBrand(Context context, String selectedBrand) {
//        Set<String> modelsList = new HashSet<>();
//        TreeSet<String> modelsList = new TreeSet<>();
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
        } catch (Exception e)
        {
            utilityClass.showAlertExp();
//            e.printStackTrace();
//            Log.e("CSVDataReader", "Error reading CSV file: " + e.getMessage());
        }

        return new ArrayList<>(modelsList);
    }
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
        } catch (Exception e)
        {
            utilityClass.showAlertExp();
//            e.printStackTrace();
//            Log.e("CSVDataReader", "Error reading CSV file: " + e.getMessage());
        }

        return capcitylist;
    }
    public String getprice1(Context context, String selectedBrand, String selectedModel,String selectCapcity) {

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
        } catch (Exception e)
        {
            utilityClass.showAlertExp();
//            e.printStackTrace();
//            Log.e("CSVDataReader", "Error reading CSV file: " + e.getMessage());
        }

        return null;
    }
    public String getpriceByCode(Context context, String selectedBrand, String selectedModel,String selectCapcity,int code) {

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
                        return prices[code-1];
                    }
                }
            }

            reader.close();
        } catch (Exception e)
        {
            utilityClass.showAlertExp();
//            e.printStackTrace();
//            Log.e("CSVDataReader", "Error reading CSV file: " + e.getMessage());
        }

        return null;
    }
}
