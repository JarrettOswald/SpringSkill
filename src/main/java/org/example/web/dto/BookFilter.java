package org.example.web.dto;


public class BookFilter {
    private String row;

    private String value;

    public BookFilter() {
    }

    public BookFilter(String value, String row) {
        this.row = row;
        this.value = value;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FilterBook{" +
                "row='" + row + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
