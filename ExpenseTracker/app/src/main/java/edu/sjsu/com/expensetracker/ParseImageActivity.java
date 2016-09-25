package edu.sjsu.com.expensetracker;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ParseImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_image);

        copyTrainingData();

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.receipt);

        TessBaseAPI baseApi = new TessBaseAPI();
        // DATA_PATH = Path to the storage
        // lang = for which the language data exists, usually "eng"
        baseApi.init(getFilesDir().getAbsolutePath(), "eng");
        // Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        baseApi.setImage(icon);
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        System.err.println("recognizedText " + recognizedText);
    }

    private void copyTrainingData()
    {
        String tessdataString = getFilesDir() + File.separator + "tessdata";
        File trainingData = new File(tessdataString);
        if(!trainingData.exists())
        {
            AssetManager assetManager = getAssets();

            try{

                InputStream in = null;
                OutputStream out = null;

                in = assetManager.open("eng.traineddata");
                if(!(new File(tessdataString).exists()))
                {
                    new File(tessdataString).mkdir();
                }
                String outString = tessdataString + File.separator + "eng.traineddata";
                File outFile = new File(outString);
                out = new FileOutputStream(outFile);

                copyFile(in, out);
                in.close();
                out.flush();
                out.close();

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
