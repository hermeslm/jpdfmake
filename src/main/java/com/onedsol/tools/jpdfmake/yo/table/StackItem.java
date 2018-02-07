package com.onedsol.tools.jpdfmake.yo.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.yo.Item;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StackItem extends Item {
    ArrayList<Object> stack = new ArrayList<>();

    public ArrayList<Object> getStack() {
        return stack;
    }

    public void setStack(ArrayList<Object> stack) {
        this.stack = stack;
    }

    public void addStackItem(Item item) {
        stack.add(item);
    }

    public void addStackItem(Object item) {
        stack.add(item);
    }
}
