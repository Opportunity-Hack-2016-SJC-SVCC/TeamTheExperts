package edu.sjsu.com.expensetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageExpense extends AppCompatActivity {

    private List<expenseCardView> expenseCardViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_expense);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycleview);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        expenseCardViewList = new ArrayList<>();
        expenseCardViewList.add(new expenseCardView("Today","You spent $10 at Walmart"));
        expenseCardViewList.add(new expenseCardView("Yesterday", "You spent $16 at Target"));
        expenseCardViewList.add(new expenseCardView("Yesterday", "You spent $6 at La Vic"));

        final ExpenseCardViewAdaptor adapter = new ExpenseCardViewAdaptor(expenseCardViewList);
        rv.setAdapter(adapter);
    }
}
