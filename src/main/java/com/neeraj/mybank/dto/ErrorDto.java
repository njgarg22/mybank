package com.neeraj.mybank.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {
    private String errorMsg;
    private List<String> fieldNames = new ArrayList<>();

    public ErrorDto(String errorMsg, List<String> fields) {
        this.errorMsg = errorMsg;
        this.fieldNames = fields;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }
}
