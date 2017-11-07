package com.example.gszzz.slaproject.server_interaction;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gszzz.slaproject.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private String sourceFilePath;
    private int count = 0;

    public FileUploadAsyncTask(Context context) {this.context = context;}


    @Override
    protected String doInBackground(String... params) {

        //See if it is image. If is,
//        boolean isImageFile = false;
//        if (params[0].contains(".png")) isImageFile = true;

//        Toast.makeText(context, "INSIDE...", Toast.LENGTH_SHORT).show();

        try {
            String sourceFileUri = params[0];
            sourceFilePath = sourceFileUri;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (sourceFile.isFile()) {


                try {
                    String upLoadServerUri = context.getString(R.string.ip_address) + "/SLAProject/LoginForm/upload_csv.php?";

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE",
                            "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("bill", sourceFileUri);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                            + sourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);
                        count += 1;

                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);



                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();


                    if (serverResponseCode == 200) {

                        // messageText.setText(msg);
                        //Toast.makeText(ctx, "File Upload Complete.",
                        //      Toast.LENGTH_SHORT).show();

                        // recursiveDelete(mDirectory1);
                    }



                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();



                } catch (Exception e) {

                    // dialog.dismiss();
                    e.printStackTrace();

                }
                // dialog.dismiss();

            }

        } catch (Exception ex) {
            // dialog.dismiss();

            ex.printStackTrace();
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
//        File file = new File(sourceFilePath);
//        if (!file.delete()) {
//            Toast.makeText(context, "Failed to delete local file...", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Local file deleted successfully...", Toast.LENGTH_SHORT).show();
//        }
//        Toast.makeText(context, "Uploading done..." + "_" + result, Toast.LENGTH_SHORT).show();
        context = null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}