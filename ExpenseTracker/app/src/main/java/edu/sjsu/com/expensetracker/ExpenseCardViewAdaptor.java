package edu.sjsu.com.expensetracker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by patel on 9/25/2016.
 */
public class ExpenseCardViewAdaptor extends RecyclerView.Adapter<ExpenseCardViewAdaptor.favcardadapterholder> {
    private Context context;
    List<ExpenseCardView> favcardviews;

    ExpenseCardViewAdaptor(List<ExpenseCardView> favcardviews){
        this.favcardviews = favcardviews;
    }
    public static class favcardadapterholder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView date;
        TextView amount;

        favcardadapterholder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            date = (TextView)itemView.findViewById(R.id.text_date);
            amount = (TextView)itemView.findViewById(R.id.text_expense);
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
        favcardadapterholders.date.setText(favcardviews.get(position).date);
        favcardadapterholders.amount.setText(favcardviews.get(position).amount);



    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
