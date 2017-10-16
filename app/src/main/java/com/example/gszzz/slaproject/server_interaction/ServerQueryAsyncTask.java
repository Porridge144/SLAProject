package com.example.gszzz.slaproject.server_interaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gszzz.slaproject.BuildingSelectionForm;
import com.example.gszzz.slaproject.MainActivity;
import com.example.gszzz.slaproject.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ServerQueryAsyncTask extends AsyncTask<String, Void, String> {

    public static final String SURVEY_LIST_QUERY_RESULT = "survey_list_result_intent";
    public static final String LEVEL_LIST_QUERY_RESULT = "level_list_result_intent";
    private Context ctx;
    private String username = "";

    public ServerQueryAsyncTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
//        alertDialog = new AlertDialog.Builder(ctx).create();
//        alertDialog.setTitle("Login Information");
    }

    @Override
    protected String doInBackground(String... params) {
//------------------------------Change Server IP HERE---------------------------------------
        String regUrl = ctx.getString(R.string.ip_address) + "/SLAProject/register.php";
        String loginUrl = ctx.getString(R.string.ip_address) + "/SLAProject/login.php";
        String surveyListQueryUrl = ctx.getString(R.string.ip_address) + "/SLAProject/UserDataQueryHandling/surveylistquery.php";
        String levelListQueryUrl = ctx.getString(R.string.ip_address) + "/SLAProject/UserDataQueryHandling/levellistquery.php";
//        String regUrl = "http://172.17.2.191:8081/attendance/register.php";
//        String loginUrl = "http://172.17.2.191:8081/attendance/login.php";
//        String regUrl = "http://172.17.2.191:8081/attendance/UserDataQueryHandling/surveylistquery.php";
//        String loginUrl = "http://172.17.2.191:8081/attendance/UserDataQueryHandling/levellistquery.php";
//------------------------------------------------------------------------------------------
        String method = params[0];
        if (method.equals("register")) {
            String username = params[1];
            String password = params[2];
            try {
                URL url = new URL(regUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return "Registration Success...";
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (method.equals("login")) {
            String username = params[1];
            String password = params[2];
            this.username = username;
            try {
                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Get response from server!!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (method.equals("surveylistquery")) {
            String username = params[1];
            try {
                URL url = new URL(surveyListQueryUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Get response from server!!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (method.equals("levellistquery")) {
            String surveyname = params[1];
            try {
                URL url = new URL(levelListQueryUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("surveyname", "UTF-8") + "=" + URLEncoder.encode(surveyname, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Get response from server!!
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
//        super.onPostExecute(result);1111
        if (result.equals("Registration Succeeded...")) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            //close registration activity and go back to login page
            Intent i = new Intent(ctx, LoginForm.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }else {
            if (result.contains("Login Succeeded...Welcome")){
                Intent i = new Intent(ctx, MainActivity.class);
                i.putExtra("username", this.username);
                ctx.startActivity(i);
                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            } else if (result.equals("Username already existed. Registration failed...") || result.equals("Login Failed...Try Again")) {
                //Do nothing
                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            } else {
                //When receive response from user data query...
                if (result.contains("surveyListQuery")) {
                    Intent surveyListIntent = new Intent(SURVEY_LIST_QUERY_RESULT);
                    surveyListIntent.putExtra("surveyNameString", result);
                    ctx.sendBroadcast(surveyListIntent);
                } else if (result.contains("levelListQuery")) {
                    Intent levelListIntent = new Intent(LEVEL_LIST_QUERY_RESULT);
                    levelListIntent.putExtra("levelNameString", result);
                    ctx.sendBroadcast(levelListIntent);
                } else {
                    //No survey or level info found...
                    Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
                }
            }


        }
    }
}
