package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnInfo {
    String catalogName;
    String columnClassName;
    String columnLabel;
    String columnName;
    int columnType;
    String columnTypeName;
    int precision;
    int scale;
}
