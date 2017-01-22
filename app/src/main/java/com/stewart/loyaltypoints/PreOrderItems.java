package com.stewart.loyaltypoints;

/**
 * Created by stewart on 22/01/2017.
 */

public class PreOrderItems {
    private String product;
    private Long itemQuanity;

    public PreOrderItems() {
    }

    public PreOrderItems(String product, Long itemQuanity) {
        this.product = product;
        this.itemQuanity = itemQuanity;
    }

    public PreOrderItems(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getItemQuanity() {
        return itemQuanity;
    }

    public void setItemQuanity(Long itemQuanity) {
        this.itemQuanity = itemQuanity;
    }
}
