package com.minicafe.csv;

import java.util.List;
import java.util.stream.Collectors;

public interface CsvRow {

    String COLUMN_SEPARATOR = ",";

    List<Object> values();

    default String toCsvRow(){
        return values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
