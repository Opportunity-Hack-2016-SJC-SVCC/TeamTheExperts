package edu.sjsu.com.expensetracker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by patel on 9/25/2016.
 */
public class ExpenseCardViewAdaptor extends RecyclerView.Adapter<ExpenseCardViewAdaptor.favcardadapterholder> {
    private Context context;
    List<PlanExpense> favcardviews;

    NumberFormat formatter;

    String[] stores = {"Walmart", "Tessco", "Target", "Walgreens", "Safeway", "Costco"};

    ExpenseCardViewAdaptor(Context context, List<PlanExpense> favcardviews){
        this.favcardviews = favcardviews;
        this.context = context;

        formatter = NumberFormat.getCurrencyInstance();
    }
    public static class favcardadapterholder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView date;
        TextView amount;
        Button necessaryButton;
        Button unnecessaryButton;

        favcardadapterholder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            date = (TextView)itemView.findViewById(R.id.text_date);
            amount = (TextView)itemView.findViewById(R.id.text_expense);
            necessaryButton = (Button) itemView.findViewById(R.id.necessary_button);
            unnecessaryButton = (Button) itemView.findViewById(R.id.unnecessary_button);
        }
    }


    @Override
    public int getItemCount() {
        return favcardviews.size();
    }
    @Override
    public favcardadapterholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_card, viewGroup, false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expense_card, viewGroup, false);
        favcardadapterholder pvh = new favcardadapterholder(view);
        return pvh;
    }
    @Override
    public void onBindViewHolder(favcardadapterholder favcardadapterholders, final int position) {

        Date date=new Date(Long.parseLong(favcardviews.get(position).timeStamp));

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        favcardadapterholders.date.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));

        ArrayList<String> products = favcardviews.get(position).values;

        float total = 0;

        for (String product:products)
        {
            String[] splitProducts = product.split(" ");
            total += Float.parseFloat(splitProducts[splitProducts.length - 1]);
            System.err.println(splitProducts[splitProducts.length - 1]);
        }

        favcardadapterholders.amount.setText("You spent "+formatter.format(total)+" at "+stores[new Random().nextInt(stores.length - 1)]);

        favcardadapterholders.unnecessaryButton.setTag(position);
        favcardadapterholders.unnecessaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();

                ((ManageExpenseActivity) context).moveUnnecessaryExpense(position);
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
