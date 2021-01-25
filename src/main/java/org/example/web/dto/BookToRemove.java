package org.example.web.dto;

import javax.validation.constraints.NotEmpty;

public class BookToRemove {

    private String row;

    @NotEmpty
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "BookIdToRemove{" +
                "row='" + row + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
