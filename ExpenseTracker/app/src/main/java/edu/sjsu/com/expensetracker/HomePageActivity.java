package edu.sjsu.com.expensetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private Button addIncomeButton;
    private Button addExpenseButton;
    private Button manageExpensesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        addIncomeButton = (Button) findViewById(R.id.add_income_button);
        addExpenseButton = (Button) findViewById(R.id.add_expense_button);
        manageExpensesButton = (Button) findViewById(R.id.manage_expenses_button);

        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ExpenseImageActivity.class);
                startActivity(intent);
            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ExpenseImageActivity.class);
                startActivity(intent);
            }
        });

        manageExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ManageExpenseActivity.class);
                startActivity(intent);
            }
        });
    }
}
