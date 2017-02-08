package com.stewart.loyaltypoints.models;

/**
 * Created by stewart on 08/02/2017.
 */

public class Transactions {
    //Item Name
    private String Item0, Item1, Item2, Item3;
    //Item Qty
    private String ItemQty0, ItemQty1, ItemQty2, ItemQty3;
    //Date of Order
    private String OrderDate;
    //Price
    //Needs implementing on the PreOrderActivty
    private String ItemPrice;

    public Transactions() {
    }

    public String getItem0() {
        return Item0;
    }

    public void setItem0(String item0) {
        Item0 = item0;
    }

    public String getItem1() {
        return Item1;
    }

    public void setItem1(String item1) {
        Item1 = item1;
    }

    public String getItem2() {
        return Item2;
    }

    public void setItem2(String item2) {
        Item2 = item2;
    }

    public String getItem3() {
        return Item3;
    }

    public void setItem3(String item3) {
        Item3 = item3;
    }

    public String getItemQty0() {
        return ItemQty0;
    }

    public void setItemQty0(String itemQty0) {
        ItemQty0 = itemQty0;
    }

    public String getItemQty1() {
        return ItemQty1;
    }

    public void setItemQty1(String itemQty1) {
        ItemQty1 = itemQty1;
    }

    public String getItemQty2() {
        return ItemQty2;
    }

    public void setItemQty2(String itemQty2) {
        ItemQty2 = itemQty2;
    }

    public String getItemQty3() {
        return ItemQty3;
    }

    public void setItemQty3(String itemQty3) {
        ItemQty3 = itemQty3;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }
}
