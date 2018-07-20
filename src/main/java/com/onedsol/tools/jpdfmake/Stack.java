package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stack extends Item {

    ArrayList<Object> stack = new ArrayList<>();

    public Stack stack(ArrayList<Object> stack) {

        this.stack = stack;
        return this;
    }

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
