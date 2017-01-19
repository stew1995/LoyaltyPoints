package com.stewart.loyaltypoints;

/**
 * Created by stewart on 18/01/2017.
 */

public class Items {
    private String itemName, itemImage, itemPrice;
    private Long itemPoints;


    public Items (){

    }

    public Items(String Name, String Price, Long Points, String itemImage) {
        this.itemName = Name;
        this.itemPrice = Price;
        this.itemPoints = Points;
        this.itemImage = itemImage;
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

    public Long getItemPoints() {
        return itemPoints;
    }

    public void setItemPoints(Long itemPoints) {
        this.itemPoints = itemPoints;
    }


    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
