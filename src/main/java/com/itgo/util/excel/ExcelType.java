package com.itgo.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public enum ExcelType {

    EXCEL_2003(".xls"),
    EXCEL_2007(".xlsx");

    private String suffix;


    ExcelType(String suffix){
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Workbook getWorkbook(){
        if(this.equals(ExcelType.EXCEL_2003))
            return  new HSSFWorkbook();
        else
            return  new XSSFWorkbook();
    }
}
