package edu.sjsu.com.expensetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ManageExpenseActivity extends AppCompatActivity {

    private List<ExpenseCardView> expenseCardViewList;

    private DatabaseReference mDatabase;

    private ArrayList<PlanExpense> expensesList = new ArrayList<>();
    private ArrayList<PlanExpense> unnecessaryExpensesList = new ArrayList<>();

    private ProgressDialog mProgressDialog;

    private ExpenseCardViewAdaptor adapter;

    private RecyclerView rv;

    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_expense);

        doneButton = (Button) findViewById(R.id.done_button);

        rv = (RecyclerView)findViewById(R.id.recycleview);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Retrieving...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

//        expenseCardViewList = new ArrayList<>();
//        expenseCardViewList.add(new ExpenseCardView("Today","You spent $10 at Walmart"));
//        expenseCardViewList.add(new ExpenseCardView("Yesterday", "You spent $16 at Target"));
//        expenseCardViewList.add(new ExpenseCardView("Yesterday", "You spent $6 at La Vic"));
//
//        final ExpenseCardViewAdaptor adapter = new ExpenseCardViewAdaptor(expenseCardViewList);
//        rv.setAdapter(adapter);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageExpenseActivity.this, ExpensesGraphActivity.class);
                intent.putExtra("necessaryExpenses", expensesList);
                intent.putExtra("unnecessaryExpenses", unnecessaryExpensesList);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child("apoorv9990").child("expenses").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> expenseSnapshot = dataSnapshot.getChildren().iterator();
                while(expenseSnapshot.hasNext()) {
                    DataSnapshot current = expenseSnapshot.next();

                    PlanExpense planExpense = new PlanExpense();
                    planExpense.timeStamp = current.getKey();
                    planExpense.values = (ArrayList<String> )((HashMap<String, Object>) current.getValue()).get("products");

                    expensesList.add(planExpense);
                }

                adapter = new ExpenseCardViewAdaptor(ManageExpenseActivity.this, expensesList);
                rv.setAdapter(adapter);

                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ManageExpenseActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void moveUnnecessaryExpense(int position)
    {
        PlanExpense unnecessaryExpense = expensesList.remove(position);
        unnecessaryExpensesList.add(unnecessaryExpense);
        adapter.notifyDataSetChanged();
    }
}