package edu.sjsu.com.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TargetActivity extends AppCompatActivity {

    private Button setTargetButton;

    private EditText setTargetEditText;

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        setContentView(R.layout.target_activity);

        setTargetButton = (Button) findViewById(R.id.set_target_button);
        setTargetEditText = (EditText) findViewById(R.id.target_edit_text);

        setTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putFloat("target", Float.parseFloat(setTargetEditText.getText().toString()));

                Intent intent = new Intent(TargetActivity.this, HomePageActivity.class);
                startActivity(intent);
                TargetActivity.this.finish();
            }
        });
    }
}
