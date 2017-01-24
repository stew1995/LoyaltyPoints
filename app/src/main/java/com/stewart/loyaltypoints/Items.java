package com.stewart.loyaltypoints;

import java.util.List;


/**
 * Created by stewart on 18/01/2017.
 */

public class Items {
    private String itemName, itemImage, itemPrice, itemPreOrderLocation;
    private Long itemPoints;
    private String itemQty;
    private List<String> itemPreOrderName;
    private String Uid;



    public Items (){

    }

    public Items(String itemName) {
        this.itemName = itemName;
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

    public String setItemName(String itemName) {
        this.itemName = itemName;
        return itemName;
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

    public String getUid() {
        return Uid;
    }

    public String setUid(String uid) {
        Uid = uid;
        return uid;
    }
}
