package edu.sjsu.com.expensetracker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ParseImageActivity extends AppCompatActivity {

    private ListView parsedListView;

    private ParseImageListAdapter parseImageListAdapter;

    private ArrayList<String> recognizedTextArrayList = new ArrayList<>();

    private ProgressDialog mProgressDialog;

    private DatabaseReference mDatabase;

    private Button saveExpenseButton;

    float alreadySpent = 753;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_image);

        parsedListView = (ListView) findViewById(R.id.parsed_image_list);

        saveExpenseButton = (Button) findViewById(R.id.save_expense_button);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Processing...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        new ParseImage().execute();

        saveExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference();

                HashMap<String, Object> map = new HashMap<>();
                map.put("necessary", true);
                map.put("products", recognizedTextArrayList);

                mDatabase.child("users").child("apoorv9990").child("expenses").child(""+System.currentTimeMillis()).setValue(map);

                float total = 0;

                for(String product : recognizedTextArrayList)
                {
                    String[] splitText = product.split(" ");
                    total += Float.parseFloat(splitText[splitText.length - 1]);
                }

                final Dialog dialog = new Dialog(ParseImageActivity.this);

                View successView = ParseImageActivity.this.getLayoutInflater().inflate(R.layout.expense_exceeded_dialog, null);

                LinearLayout.LayoutParams successViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                dialog.setContentView(successView, successViewLayoutParams);

                Point size = new Point();
                getWindowManager().getDefaultDisplay().getSize(size);

                WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
                lp.width= (int) (size.x - (size.x * 0.1));

                TextView dialogMessage = (TextView) dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText("You have spent $52.47 this month. Almost 77% of your target expense. Please be careful!");

                Button okButton = (Button) dialog.findViewById(R.id.ok_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Intent intent = new Intent(ParseImageActivity.this, HomePageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                dialog.show();

            }
        });
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

    private String parseImage()
    {
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.receipt_new);

        TessBaseAPI baseApi = new TessBaseAPI();
        // DATA_PATH = Path to the storage
        // lang = for which the language data exists, usually "eng"
        baseApi.init(getFilesDir().getAbsolutePath(), "eng");
        // Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        baseApi.setImage(icon);
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();

        return recognizedText;
    }

    private class ParseImage extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

            copyTrainingData();

            String recognizedText = parseImage();

            String[] recognizedTextArray = recognizedText.split("\n");

            for(int i = 0; i < recognizedTextArray.length; i++)
            {
                recognizedTextArrayList.add(recognizedTextArray[i]);
                System.err.println(recognizedTextArray[i]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            parseImageListAdapter = new ParseImageListAdapter(ParseImageActivity.this, R.layout.image_list_row_layout, recognizedTextArrayList);

            parsedListView.setAdapter(parseImageListAdapter);

            mProgressDialog.dismiss();

            super.onPostExecute(aVoid);
        }
    }
}
