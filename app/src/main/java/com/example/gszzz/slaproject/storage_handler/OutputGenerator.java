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

    private static int count = 0;

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
        csvText.append("Location" + DELIMITER + "Type" + DELIMITER + "Element" + DELIMITER + "Material" + DELIMITER + "Defect" + DELIMITER + "CC Rating" + DELIMITER + ENDLINE);
        //Content
        String[] levelNames = sp.getString("levelNames", "").split(":");
        String[] structuralElementsArray = context.getResources().getStringArray(R.array.structuralElementsArray);
        String[] architecturalElementsArray = context.getResources().getStringArray(R.array.architecturalElementsArray);
        String[] auxiliaryElementsArray = context.getResources().getStringArray(R.array.auxiliaryElementsArray);
        String[] combinedElementsArray = generalConcatAll(structuralElementsArray, architecturalElementsArray, auxiliaryElementsArray);

        for (String levelName: levelNames) {

            String[] rooms = sp.getString(levelName + "RoomList", "").split(":");
            for (String roomName : rooms) {
                String location = levelName + "_" + roomName;
//                csvText.append(location).append(DELIMITER).append(ENDLINE);
                csvText.append(location);

                int count = 1;
                int thresholdNumber1 = structuralElementsArray.length;
                int thresholdNumber2 = structuralElementsArray.length + architecturalElementsArray.length;
                String typeString = "structural";

                for (String combinedElement : combinedElementsArray) {
                    String elementIndicator = location + "_" + combinedElement + "_" + "ifElementExist";
                    if (sp.getBoolean(elementIndicator, false)) {
                        csvText.append(DELIMITER).append(typeString).append(DELIMITER).append(combinedElement).append(DELIMITER);
                        String timberCheckBoxContentStringName = location + "_" + combinedElement + "_" + "timberCheckBoxContentString";
                        if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Timber").append(DELIMITER).append(separateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                        }
                        String masonaryCheckBoxContentStringName = location + "_" + combinedElement + "_" + "masonaryCheckBoxContentString";
                        if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Masonry").append(DELIMITER).append(separateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                        }
                        String concreteCheckBoxContentStringName = location + "_" + combinedElement + "_" + "concreteCheckBoxContentString";
                        if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Concrete").append(DELIMITER).append(separateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                        }
                        String metalworksCheckBoxContentStringName = location + "_" + combinedElement + "_" + "metalworksCheckBoxContentString";
                        if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Metalworks").append(DELIMITER).append(separateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                        }
                        String generalCheckBoxContentStringName = location + "_" + combinedElement + "_" + "generalCheckBoxContentString";
                        if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("General").append(DELIMITER).append(separateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                        }
                        csvText.setLength(csvText.length() - 3);
                        csvText.setLength(csvText.length() - 1);
                        csvText.append(sp.getString(location + "_" + combinedElement + "_" + "conditionClassButtonString", "NONE")).append(ENDLINE);
                    }
                    if (count == thresholdNumber1) typeString = "architectural";
                    if (count == thresholdNumber2) typeString ="auxiliary";
                    count += 1;
                }

/*                for (String elementName : architecturalElementsArray) {
                    String elementIndicator = location + "_" + elementName + "_" + "ifElementExist";
                    if (sp.getBoolean(elementIndicator, false)) {
                        csvText.append(DELIMITER).append(elementName).append(DELIMITER);
                        String timberCheckBoxContentStringName = location + "_" + elementName + "_" + "timberCheckBoxContentString";
                        if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Timber").append(DELIMITER).append(separateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String masonaryCheckBoxContentStringName = location + "_" + elementName + "_" + "masonaryCheckBoxContentString";
                        if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Masonry").append(DELIMITER).append(separateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String concreteCheckBoxContentStringName = location + "_" + elementName + "_" + "concreteCheckBoxContentString";
                        if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Concrete").append(DELIMITER).append(separateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String metalworksCheckBoxContentStringName = location + "_" + elementName + "_" + "metalworksCheckBoxContentString";
                        if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("Metalworks").append(DELIMITER).append(separateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        String generalCheckBoxContentStringName = location + "_" + elementName + "_" + "generalCheckBoxContentString";
                        if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                            csvText.append("General").append(DELIMITER).append(separateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                            csvText.append(DELIMITER).append(DELIMITER);
                        }
                        csvText.setLength(csvText.length() - 2);
                        csvText.setLength(csvText.length() - 1);
                        csvText.append(sp.getString(location + "_" + elementName + "_" + "conditionClassButtonString", "")).append(ENDLINE);
                    }

                }*/




            }

        }

        csvText.append(ENDLINE).append(ENDLINE);


        String[] elevationNames = context.getResources().getStringArray(R.array.elevationNamesArray);
        String[] roofNames= context.getResources().getStringArray(R.array.roofNamesArray);
        String[] landscapeNames = context.getResources().getStringArray(R.array.landscapeNamesArray);
        String[] combinedNames = generalConcatAll(elevationNames, roofNames, landscapeNames);




        for (String combinedName: combinedNames) {
            boolean ifElevationExist = false;
            csvText.append(combinedName);

            int count = 1;
            int thresholdNumber1 = structuralElementsArray.length;
            int thresholdNumber2 = structuralElementsArray.length + architecturalElementsArray.length;
            String typeString = "structural";

            for (String combinedElement : combinedElementsArray) {
                String elementIndicator = combinedName + "_" + combinedName + "_" + combinedElement + "_" + "ifElementExist";
                if (sp.getBoolean(elementIndicator, false)) {
                    ifElevationExist = true;
                    csvText.append(DELIMITER).append(typeString).append(DELIMITER).append(combinedElement).append(DELIMITER);
                    String timberCheckBoxContentStringName = combinedName + "_" + combinedName + "_" + combinedElement + "_" + "timberCheckBoxContentString";
                    if (!sp.getString(timberCheckBoxContentStringName, "").equals("") && !sp.getString(timberCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Timber").append(DELIMITER).append(separateDefects(sp.getString(timberCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                    }
                    String masonaryCheckBoxContentStringName = combinedName + "_" + combinedName + "_" + combinedElement + "_" + "masonaryCheckBoxContentString";
                    if (!sp.getString(masonaryCheckBoxContentStringName, "").equals("") && !sp.getString(masonaryCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Masonry").append(DELIMITER).append(separateDefects(sp.getString(masonaryCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                    }
                    String concreteCheckBoxContentStringName = combinedName + "_" + combinedName + "_" + combinedElement + "_" + "concreteCheckBoxContentString";
                    if (!sp.getString(concreteCheckBoxContentStringName, "").equals("") && !sp.getString(concreteCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Concrete").append(DELIMITER).append(separateDefects(sp.getString(concreteCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                    }
                    String metalworksCheckBoxContentStringName = combinedName + "_" + combinedName + "_" + combinedElement + "_" + "metalworksCheckBoxContentString";
                    if (!sp.getString(metalworksCheckBoxContentStringName, "").equals("") && !sp.getString(metalworksCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("Metalworks").append(DELIMITER).append(separateDefects(sp.getString(metalworksCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                    }
                    String generalCheckBoxContentStringName = combinedName + "_" + combinedName + "_" + combinedElement + "_" + "generalCheckBoxContentString";
                    if (!sp.getString(generalCheckBoxContentStringName, "").equals("") && !sp.getString(generalCheckBoxContentStringName, "").equals("NA")) {
                        csvText.append("General").append(DELIMITER).append(separateDefects(sp.getString(generalCheckBoxContentStringName, ""))).append(DELIMITER).append(ENDLINE);
                        csvText.append(DELIMITER).append(DELIMITER).append(DELIMITER);
                    }
                    csvText.setLength(csvText.length() - 3);
                    csvText.setLength(csvText.length() - 1);
                    csvText.append(sp.getString(combinedName + "_" + combinedName + "_" + combinedElement + "_" + "conditionClassButtonString", "NONE")).append(ENDLINE);
                    count += 1;
                }

                if (count == thresholdNumber1) typeString = "architectural";
                if (count == thresholdNumber2) typeString ="auxiliary";
                count += 1;
            }

            if (!ifElevationExist) {
                csvText.append(DELIMITER).append("NA").append(DELIMITER).append(ENDLINE);
            }
        }

        csvText.append(ENDLINE).append(ENDLINE).append("Count: ").append(count);


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

    public String[] generalConcatAll(String[]... jobs) {
        int len = 0;
        for (final String[] job : jobs) {
            len += job.length;
        }

        final String[] result = new String[len];

        int currentPos = 0;
        for (final String[] job : jobs) {
            System.arraycopy(job, 0, result, currentPos, job.length);
            currentPos += job.length;
        }

        return result;
    }


    private String separateDefects(String originalString){

        String[] splittedString = originalString.split(":");
        StringBuilder processedString = new StringBuilder("");
        processedString.append(splittedString[0]);
        if (splittedString.length > 1) {
            for (int i = 1; i < splittedString.length; i++) {
                processedString.append(DELIMITER).append(ENDLINE).append(DELIMITER)
                        .append(DELIMITER).append(DELIMITER).append(DELIMITER).append(splittedString[i]);
            }
        }

        return processedString.toString();
    }

}
