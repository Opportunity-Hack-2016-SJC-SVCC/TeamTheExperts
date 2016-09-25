package edu.sjsu.com.expensetracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
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

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.parseColor("#27ae60"));
        colors.add(Color.parseColor("#c0392b"));

        pieDataSet.setColors(colors);

        pieChart.getLegend().setComputedColors(colors);
        pieChart.getLegend().setComputedLabels(labels);

        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setDrawInside(false);
        pieChart.getLegend().setXEntrySpace(7f);
        pieChart.getLegend().setYEntrySpace(0f);
        pieChart.getLegend().setYOffset(0f);

        PieData data = new PieData(pieDataSet);
        pieChart.setData(data);
    }
}
