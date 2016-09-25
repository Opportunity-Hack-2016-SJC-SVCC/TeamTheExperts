package edu.sjsu.com.expensetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ExpensesGraphActivity extends AppCompatActivity {

    private ArrayList<PlanExpense> necessaryExpenses;
    private ArrayList<PlanExpense> unnecessaryExpenses;

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_graph_activity);

        pieChart = (PieChart) findViewById(R.id.piechart);

        if(getIntent().hasExtra("necessaryExpenses"))
        {
            necessaryExpenses = (ArrayList<PlanExpense>) getIntent().getSerializableExtra("necessaryExpenses");
            unnecessaryExpenses = (ArrayList<PlanExpense>) getIntent().getSerializableExtra("unnecessaryExpenses");
        }

        ArrayList<PieEntry> entries = new ArrayList<>();

        float necessaryExpense = 0;

        for(PlanExpense expense : necessaryExpenses)
        {
            for(String product : expense.values)
            {
                String[] splitProducts = product.split(" ");
                necessaryExpense += Float.parseFloat(splitProducts[splitProducts.length - 1]);
            }
        }
        entries.add(new PieEntry(necessaryExpense, 0));

        float unnecessaryExpense = 0;

        for(PlanExpense expense : unnecessaryExpenses)
        {
            for(String product : expense.values)
            {
                String[] splitProducts = product.split(" ");
                unnecessaryExpense += Float.parseFloat(splitProducts[splitProducts.length - 1]);
            }
        }

        entries.add(new PieEntry(unnecessaryExpense, 0));

        PieDataSet pieDataSet = new PieDataSet(entries, "Expenses");

        ArrayList<String> labels = new ArrayList<>();

        labels.add("Necessary");
        labels.add("Unnecessary");

        PieData data = new PieData(pieDataSet);
        pieChart.setData(data);
    }
}
