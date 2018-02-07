package com.onedsol.tools.jpdfmake.yo;

import java.util.ArrayList;

public class TextsItem extends Item{
    ArrayList<Item> text = new ArrayList<>();

    public ArrayList<Item> getText() {
        return text;
    }

    public void setText(ArrayList<Item> text) {
        this.text = text;
    }

    public void addTextItem(Item item) {
        text.add(item);
    }
}
