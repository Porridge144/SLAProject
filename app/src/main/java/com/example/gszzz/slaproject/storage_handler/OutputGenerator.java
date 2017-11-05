package com.example.gszzz.slaproject.storage_handler;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.gszzz.slaproject.R;
import com.example.gszzz.slaproject.server_interaction.FileUploadAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputGenerator {

    private final static String DELIMITER = ",";
    private final static String ENDLINE = "\n";


    private String fileName = "outputCSV.csv";
    private String surveyName;

    public OutputGenerator(String surveyName) {
        this.surveyName = surveyName;
    }


    public void generateCSV(Context context) {

        fileName = surveyName + "_" + fileName;

        StringBuilder csvText = new StringBuilder("");

        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.preference_filename), Context.MODE_PRIVATE);

        //Building Selection Form
        csvText.append( "Cluster").append(DELIMITER).append(sp.getString("clusterString", "")).append(ENDLINE);
        csvText.append( "Building Name").append(DELIMITER).append(sp.getString("buildingNameString", "")).append(ENDLINE);
        //Pre Info Form
        csvText.append("Address").append(DELIMITER).append(sp.getString("addressString", "")).append(ENDLINE);
        csvText.append("Inspection Date").append(DELIMITER).append(sp.getString("inspectionDateString", "")).append(ENDLINE);
        csvText.append("Inspector").append(DELIMITER).append(sp.getString("inspectorString", "")).append(ENDLINE);
        //Basic Info Form
        csvText.append("Type").append(DELIMITER).append(sp.getString("typeButtonString", "")).append(ENDLINE);

        String otherTmp = sp.getString("typeOtherString", "");
        if (!otherTmp.equals("")) csvText.append(DELIMITER).append(otherTmp).append(ENDLINE);
        csvText.append("Vacancy").append(DELIMITER).append(sp.getString("vacancyButtonString", "")).append(ENDLINE);
        otherTmp = sp.getString("vacancyOtherString", "");
        if (!otherTmp.equals("")) csvText.append(DELIMITER).append(otherTmp).append(ENDLINE);

        csvText.append("Year Built").append(DELIMITER).append(sp.getString("yearBuiltString", "")).append(ENDLINE);

        csvText.append(ENDLINE);
        csvText.append(ENDLINE);

        //Headings
        csvText.append("Location" + DELIMITER + "Element" + DELIMITER + "Material" + DELIMITER + "Defect" + DELIMITER + "CC Rating" + DELIMITER + ENDLINE);
        //Content
        String[] levelNames = sp.getString("levelNames", "").split(":");
        String[] structuralElementsArray = context.getResources().getStringArray(R.array.structuralElementsArray);
        String[] architecturalElementsArray = context.getResources().getStringArray(R.array.architecturalElementsArray);

        for (String levelName: levelNames) {

            String[] rooms = sp.getString(levelName + "RoomList", "").split(":");
            for (String roomName : rooms) {
                String location = levelName + "_" + roomName;
//                csvText.append(location).append(DELIMITER).append(ENDLINE);
                csvText.append(location);

                for (String elementName : structuralElementsArray) {
                    String elementIndicator = location + "_" + elementName + "_" + "ifElementExist";
                    if (sp.getBoolean(elementIndicator, false)) {
                        csvText.append(DELIMITER).append(elementName).append(DELIMITER);
                        String timberCheckBoxContentStringName = location + "_" + elementName + "_" + "timberCheckBoxContentString";
                        if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Timber").append(DELIMITER).append(seperateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String masonaryCheckBoxContentStringName = location + "_" + elementName + "_" + "masonaryCheckBoxContentString";
                        if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Masonry").append(DELIMITER).append(seperateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String concreteCheckBoxContentStringName = location + "_" + elementName + "_" + "concreteCheckBoxContentString";
                        if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Concrete").append(DELIMITER).append(seperateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String metalworksCheckBoxContentStringName = location + "_" + elementName + "_" + "metalworksCheckBoxContentString";
                        if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Metalworks").append(DELIMITER).append(seperateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String generalCheckBoxContentStringName = location + "_" + elementName + "_" + "generalCheckBoxContentString";
                        if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("General").append(DELIMITER).append(seperateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        csvText.setLength(csvText.length() - 2);
                        csvText.setLength(csvText.length() - 1);
                        csvText.append(sp.getString(location + "_" + elementName + "_" + "conditionClassButtonString", "")).append(ENDLINE);
                    }

                }

                for (String elementName : architecturalElementsArray) {
                    String elementIndicator = location + "_" + elementName + "_" + "ifElementExist";
                    if (sp.getBoolean(elementIndicator, false)) {
                        csvText.append(DELIMITER).append(elementName).append(DELIMITER);
                        String timberCheckBoxContentStringName = location + "_" + elementName + "_" + "timberCheckBoxContentString";
                        if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Timber").append(DELIMITER).append(seperateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String masonaryCheckBoxContentStringName = location + "_" + elementName + "_" + "masonaryCheckBoxContentString";
                        if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Masonry").append(DELIMITER).append(seperateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String concreteCheckBoxContentStringName = location + "_" + elementName + "_" + "concreteCheckBoxContentString";
                        if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Concrete").append(DELIMITER).append(seperateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String metalworksCheckBoxContentStringName = location + "_" + elementName + "_" + "metalworksCheckBoxContentString";
                        if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Metalworks").append(DELIMITER).append(seperateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String generalCheckBoxContentStringName = location + "_" + elementName + "_" + "generalCheckBoxContentString";
                        if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("General").append(DELIMITER).append(seperateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        csvText.setLength(csvText.length() - 2);
                        csvText.setLength(csvText.length() - 1);
                        csvText.append(sp.getString(location + "_" + elementName + "_" + "conditionClassButtonString", "")).append(ENDLINE);
                    }

                }




            }

        }


        String[] elevationNames = new String[] {"NorthElevation", "SouthElevation", "WestElevation", "EastElevation"};

        for (String elevationName: elevationNames) {
            boolean ifElevationExist = false;
            csvText.append(elevationName);
            for (String elementName : structuralElementsArray) {
                String elementIndicator = elevationName + "_" + elevationName + "_" + elementName + "_" + "ifElementExist";
                if (sp.getBoolean(elementIndicator, false)) {
                    ifElevationExist = true;
                    csvText.append(DELIMITER).append(elementName).append(DELIMITER);
                    String timberCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "timberCheckBoxContentString";
                    if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Timber").append(DELIMITER).append(seperateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String masonaryCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "masonaryCheckBoxContentString";
                    if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Masonry").append(DELIMITER).append(seperateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String concreteCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "concreteCheckBoxContentString";
                    if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Concrete").append(DELIMITER).append(seperateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String metalworksCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "metalworksCheckBoxContentString";
                    if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Metalworks").append(DELIMITER).append(seperateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String generalCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "generalCheckBoxContentString";
                    if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("General").append(DELIMITER).append(seperateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    csvText.setLength(csvText.length() - 2);
                    csvText.setLength(csvText.length() - 1);
                    csvText.append(sp.getString(elevationName + "_" + elevationName + "_" + elementName + "_" + "conditionClassButtonString", "")).append(ENDLINE);
                }
            }

            for (String elementName : architecturalElementsArray) {
                String elementIndicator = elevationName + "_" + elevationName + "_" + elementName + "_" + "ifElementExist";
                if (sp.getBoolean(elementIndicator, false)) {
                    ifElevationExist = true;
                    csvText.append(DELIMITER).append(elementName).append(DELIMITER);
                    String timberCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "timberCheckBoxContentString";
                    if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Timber").append(DELIMITER).append(seperateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String masonaryCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "masonaryCheckBoxContentString";
                    if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Masonry").append(DELIMITER).append(seperateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String concreteCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "concreteCheckBoxContentString";
                    if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Concrete").append(DELIMITER).append(seperateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String metalworksCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "metalworksCheckBoxContentString";
                    if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Metalworks").append(DELIMITER).append(seperateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    String generalCheckBoxContentStringName = elevationName + "_" + elevationName + "_" + elementName + "_" + "generalCheckBoxContentString";
                    if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("General").append(DELIMITER).append(seperateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER);
                    }
                    csvText.setLength(csvText.length() - 2);
                    csvText.setLength(csvText.length() - 1);
                    csvText.append(sp.getString(elevationName + "_" + elevationName + "_" + elementName + "_" + "conditionClassButtonString", "")).append(ENDLINE);
                }
            }

            if (!ifElevationExist) {
                csvText.append(DELIMITER).append("NA").append(DELIMITER).append(ENDLINE);
            }
        }


        try {
            File outputCSVFile = new File(context.getFilesDir(), fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputCSVFile);
            fileOutputStream.write(csvText.toString().getBytes());
            fileOutputStream.close();

        } catch (IOException e) {
            Toast.makeText(context.getApplicationContext(), "Fail to write to file...", Toast.LENGTH_SHORT).show();
        }

        //Call file uploader task to upload file to server
        String filePath = context.getFilesDir().toString() + "/" + fileName;
        new FileUploadAsyncTask(context).execute(filePath);

    }

    private String seperateDefects(String originalString){

        String[] splittedString = originalString.split(":");
        StringBuilder processedString = new StringBuilder("");
        processedString.append(splittedString[0]);
        if (splittedString.length > 1) {
            for (int i = 1; i < splittedString.length; i++) {
                processedString.append(DELIMITER).append(ENDLINE).append(DELIMITER)
                        .append(DELIMITER).append(DELIMITER).append(splittedString[i]);
            }
        }

        return processedString.toString();
    }

}
