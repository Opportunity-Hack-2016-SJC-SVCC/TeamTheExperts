package edu.sjsu.com.expensetracker;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ExpenseImageActivity extends AppCompatActivity {

    private ImageView billImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_image);

        billImageView = (ImageView) findViewById(R.id.bill_imageview);

        billImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseImageActivity.this, ParseImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
