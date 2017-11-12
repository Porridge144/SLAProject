package com.example.gszzz.slaproject.storage_handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.gszzz.slaproject.R;

public class StorageHandler {

    public static final String DATA_READ = "STORAGE_HANDLER_DATA_READ";
    public static final String DATA_WRITE = "STORAGE_HANDLER_DATA_WRITE";

    public static final String PAGE_BASIC_INFO = "PAGE_BASIC_INFO";
    public static final String PAGE_BUILDING_DETAIL_FORM2 = "PAGE_BUILDING_DETAIL_FORM2";
    public static final String PAGE_BUILDING_SELECTION_FORM = "PAGE_BUILDING_SELECTION_FORM";
    public static final String PAGE_PRE_INFO_FORM= "PAGE_PRE_INFO_FORM";


    public StorageHandler(){
    }


    public String[] execute(Context context, String... values) {
        if (values[0].equals(DATA_WRITE)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_filename), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            switch (values[1]){
                case PAGE_BASIC_INFO:
                    editor.putString("typeButtonString", values[2]);
                    editor.putString("vacancyButtonString", values[3]);
                    editor.putString("yearBuiltString", values[4]);
                    editor.putString("typeOtherString", values[5]);
                    editor.putString("vacancyOtherString", values[6]);

                    editor.putString("addressString", values[7]);
                    editor.putString("inspectionDateString", values[8]);
                    editor.putString("inspectorString", values[9]);
                    break;
                case PAGE_BUILDING_DETAIL_FORM2:
                    String currentLevelName = sharedPreferences.getString("currentLevelName", "");
                    String roomLabelString = sharedPreferences.getString("roomLabelString", "");
                    String itemName = currentLevelName + "_" + roomLabelString + "_" + values[2];

                    String timberCheckBoxIDStringName = itemName + "_" + "timberCheckBoxIDString";
                    String masonaryCheckBoxIDStringName = itemName + "_" + "masonaryCheckBoxIDString";
                    String concreteCheckBoxIDStringName = itemName + "_" + "concreteCheckBoxIDString";
                    String metalworksCheckBoxIDStringName = itemName + "_" + "metalworksCheckBoxIDString";
                    String generalCheckBoxIDStringName = itemName + "_" + "generalCheckBoxIDString";
                    String timberCheckBoxContentStringName = itemName + "_" + "timberCheckBoxContentString";
                    String masonaryCheckBoxContentStringName = itemName + "_" + "masonaryCheckBoxContentString";
                    String concreteCheckBoxContentStringName = itemName + "_" + "concreteCheckBoxContentString";
                    String metalworksCheckBoxContentStringName = itemName + "_" + "metalworksCheckBoxContentString";
                    String generalCheckBoxContentStringName = itemName + "_" + "generalCheckBoxContentString";

                    editor.putString(timberCheckBoxIDStringName, values[3]);
                    editor.putString(masonaryCheckBoxIDStringName, values[4]);
                    editor.putString(concreteCheckBoxIDStringName, values[5]);
                    editor.putString(metalworksCheckBoxIDStringName, values[6]);
                    editor.putString(generalCheckBoxIDStringName, values[7]);
                    editor.putString(timberCheckBoxContentStringName, values[8]);
                    editor.putString(masonaryCheckBoxContentStringName, values[9]);
                    editor.putString(concreteCheckBoxContentStringName, values[10]);
                    editor.putString(metalworksCheckBoxContentStringName, values[11]);
                    editor.putString(generalCheckBoxContentStringName, values[12]);


                    String CCRatingTmp = "";
                    if (values[13].contains("0")) CCRatingTmp = "0";
                    if (values[13].contains("1")) CCRatingTmp = "1";
                    if (values[13].contains("2")) CCRatingTmp = "2";
                    if (values[13].contains("3")) CCRatingTmp = "3";
                    editor.putString(itemName + "_" + "conditionClassButtonString", CCRatingTmp);

                    //Flag indicating existence of an element
                    String tmp = itemName + "_" + "ifElementExist";
                    editor.putBoolean(tmp, true);

//                    Toast.makeText(context, tmp, Toast.LENGTH_SHORT).show();

                    break;
                case PAGE_BUILDING_SELECTION_FORM:
                    String clusterString = values[2];
                    String buildingNameString = values[3];

                    editor.putString("clusterString", clusterString);
                    editor.putString("buildingNameString", buildingNameString);

                    break;
            }
            editor.apply();
        } else if(values[0].equals(DATA_READ)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_filename), Context.MODE_PRIVATE);
            switch (values[1]){
                case PAGE_BASIC_INFO:
                    return new String[] {
                            sharedPreferences.getString("typeButtonString", ""),
                            sharedPreferences.getString("vacancyButtonString", ""),
                            sharedPreferences.getString("yearBuiltString", ""),
                            sharedPreferences.getString("otherString", ""),

                            sharedPreferences.getString("addressString", ""),
                            sharedPreferences.getString("inspectionDateString", ""),
                            sharedPreferences.getString("inspectorString", "")
                    };

                case PAGE_BUILDING_DETAIL_FORM2:
                    String currentLevelName = sharedPreferences.getString("currentLevelName", "");
                    String roomLabelString = sharedPreferences.getString("roomLabelString", "");
                    String itemName = currentLevelName + "_" + roomLabelString + "_" + values[2];
                    String tmp1 = itemName + "_" + "ifElementExist";
                    if (sharedPreferences.getBoolean(tmp1, false)) {
                        String timberCheckBoxIDStringName = itemName + "_" + "timberCheckBoxIDString";
                        String masonaryCheckBoxIDStringName = itemName + "_" + "masonaryCheckBoxIDString";
                        String concreteCheckBoxIDStringName = itemName + "_" + "concreteCheckBoxIDString";
                        String metalworksCheckBoxIDStringName = itemName + "_" + "metalworksCheckBoxIDString";
                        String generalCheckBoxIDStringName = itemName + "_" + "generalCheckBoxIDString";

                        String timberCheckBoxIDString = sharedPreferences.getString(timberCheckBoxIDStringName, "");
                        String masonaryCheckBoxIDString = sharedPreferences.getString(masonaryCheckBoxIDStringName, "");
                        String concreteCheckBoxIDString = sharedPreferences.getString(concreteCheckBoxIDStringName, "");
                        String metalworksCheckBoxIDString = sharedPreferences.getString(metalworksCheckBoxIDStringName, "");
                        String generalCheckBoxIDString = sharedPreferences.getString(generalCheckBoxIDStringName, "");

                        String conditionClassButtonString = sharedPreferences.getString("conditionClassButtonString", "");

                        return new String[] {timberCheckBoxIDString, masonaryCheckBoxIDString, concreteCheckBoxIDString,
                                metalworksCheckBoxIDString, generalCheckBoxIDString, conditionClassButtonString};
                    }
                    break;
                case PAGE_BUILDING_SELECTION_FORM:
                    return new String[] {sharedPreferences.getString("clusterString", ""), sharedPreferences.getString("buildingNameString", "")};
//                    break;
            }

        }
        return null;
    }



}
