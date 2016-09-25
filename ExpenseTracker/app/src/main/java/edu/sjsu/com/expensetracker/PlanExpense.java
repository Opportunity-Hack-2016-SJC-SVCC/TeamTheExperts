package edu.sjsu.com.expensetracker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by patel on 9/25/2016.
 */
public class PlanExpense implements Serializable {
    boolean necessary = true;
    String timeStamp;
    ArrayList<String> values;
}
