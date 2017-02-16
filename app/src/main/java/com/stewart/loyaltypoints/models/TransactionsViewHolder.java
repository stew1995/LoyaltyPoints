package com.stewart.loyaltypoints.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.stewart.loyaltypoints.R;

import java.math.BigDecimal;

/**
 * Created by stewart on 08/02/2017.
 */

public class TransactionsViewHolder extends RecyclerView.ViewHolder {
    //Firebase
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static final int LENGTH = 1;

    private View mView;
    public TransactionsViewHolder(View itemView) {
        super( itemView );
        mView = itemView;
    }

    //Setting the CardViews
    //Item Names
    public void setItemName0(String name) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems1 );
        item_name.setText( name );
    }

    public void setItemName1(String name) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems2 );
        item_name.setText( name );
    }
    public void setItemName2(String name) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems3 );
        item_name.setText( name );
    }
    public void setItemName3(String name) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems4 );
        item_name.setText( name );
    }

    //Setting ItemQty
    public void setItemQty0(String qty) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItemsQty1 );
        item_name.setText( qty );
    }

    public void setItemQty1(String qty) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItemsQty2 );
        item_name.setText( qty );
    }

    public void setItemQty2(String qty) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItemsQty3 );
        item_name.setText( qty );
    }

    public void setItemQty3(String qty) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItemsQty4 );
        item_name.setText( qty );
    }

    //Setting Date

    public void setTransactionDate(String date) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionDate );
        item_name.setText( date );
    }

    //Location
    public void setTransactionLocation(String loc) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionLocation );
        item_name.setText( loc );
    }

    //Price
    public void setTransactionPrice(String price) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionPrice );
        item_name.setText( price );
    }

    //Points

    public void setTransactionPoints(String points) {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionPoints );
        item_name.setText( points );
    }

    //Getters
    //ItemName
    public String getItemName0() {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems1 );
        String text = (String) item_name.getText();
        return text;
    }

    public String getItemName1() {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems2 );
        String text = (String) item_name.getText();
        return text;
    }

    public String getItemName2() {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems3 );
        String text = (String) item_name.getText();
        return text;
    }

    public String getItemName3() {
        TextView item_name = (TextView) mView.findViewById( R.id.transactionItems4 );
        String text = (String) item_name.getText();
        return text;
    }



}
