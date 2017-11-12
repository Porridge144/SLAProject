package com.example.gszzz.slaproject.server_interaction;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.gszzz.slaproject.R;

public class ImageDownloadAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ImageView imageView;

    private Bitmap bmImg;

    public ImageDownloadAsyncTask(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected String doInBackground(String... args) {

        InputStream is;
        String surveyName = args[0];
        String floorPlanName = args[1];
        String image_url = context.getString(R.string.ip_address) + "/SLAProject/LoginForm/surveydata/";
        image_url = image_url + surveyName + "/" + floorPlanName;

        try {

            URL ImageUrl = new URL(image_url);

            HttpURLConnection conn = (HttpURLConnection) ImageUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bmImg = BitmapFactory.decodeStream(is);

            is.close();
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//
//            String path = ImageUrl.getPath();
//            String idStr = path.substring(path.lastIndexOf('/') + 1);
//            File filepath = Environment.getExternalStorageDirectory();
//            File dir = new File(filepath.getAbsolutePath()
//                    + "/Wallpapers/");
//            dir.mkdirs();
//            String fileName = idStr;
//            File file = new File(dir, fileName);
//            FileOutputStream fos = new FileOutputStream(file);
//            bmImg.compress(CompressFormat.JPEG, 75, fos);
//            fos.flush();
//            fos.close();
//
//            File imageFile = file;
//            MediaScannerConnection.scanFile(context,
//                    new String[] { imageFile.getPath() },
//                    new String[] { "image/jpeg" }, null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (Exception e) {
//                }
//            }
//        }
//
//        if (is != null) {
//            try {
//                is.close();
//            } catch (Exception ignored) {
//            }
//        }

        return null;
    }

    @Override
    protected void onPostExecute(String args) {

//        if (bmImg == null) {

        imageView.setImageBitmap(bmImg);


//        Toast.makeText(context, "Image still loading...",
//                Toast.LENGTH_SHORT).show();

//        }

//        else {
//
//            Toast.makeText(context, "Wallpaper Successfully Saved",
//                    Toast.LENGTH_SHORT).show();
//
//        }
    }

}