package com.vijayganduri.saltside.model;

import java.io.Serializable;
import java.util.List;

public class FeedResponse implements Serializable {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "FeedResponse{" +
                "items=" + items +
                '}';
    }
}

