package com.stewart.loyaltypoints;

/**
 * Created by stewart on 18/01/2017.
 */

public class Items {
    public String itemName;
    public String itemPrice;
    public String itemPoints;

    public Items(String Name, String Price, String Points) {
        itemName = Name;
        itemPrice = Price;
        itemPoints = Points;
    }

    public Items() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemPoints() {
        return itemPoints;
    }

    public void setItemPoints(String itemPoints) {
        this.itemPoints = itemPoints;
    }
}
