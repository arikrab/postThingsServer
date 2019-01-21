package com.company;

public class Item {

    private String itemName;
    private int phone;
    private int price;

    public Item(){
        this.itemName=null;
        this.phone=0;
        this.price=0;
    }
    public Item(String itemName, int phone, int price) {
        this.itemName = itemName;
        this.phone = phone;
        this.price = price;
    }
    public Item(Item item){
        this.itemName = item.itemName;
        this.phone = item.phone;
        this.price = item.price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
