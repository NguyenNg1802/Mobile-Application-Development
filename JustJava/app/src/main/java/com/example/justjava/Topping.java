package com.example.justjava;

public class Topping {
    private String topping;
    private int price;
    private boolean isSelected = false;

    public Topping(String topping, int price) {
        this.topping = topping;
        this.price = price;
    }

    public String getTopping() {
        return this.topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public boolean getSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
