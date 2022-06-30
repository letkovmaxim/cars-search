package org.cars.models;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private long count;
    private List<Mark> marks = new ArrayList<>();

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}
