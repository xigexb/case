package com.itgo.util.excel;

import com.itgo.annotation.ExcelField;
import com.itgo.bean.CodeStatus;
import com.itgo.bean.DataBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/1 10:11
 * @description desc:
 * Excel导入,导出工具
 */
public class ExcelUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);


    /**
     * 匹配科学计数法
     * 判断输入字符串是否为科学计数法
     * @param input
     * @return
     */
    public static boolean isENum(String input) {
        Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");
        return pattern.matcher(input).matches();
    }


    /*************************************************Excel导入**********************************************************************/


    /**
     * 总行数
     */
    private static int totalRows = 0;

    /**
     * 总工资表数
     */
    private static int totalSheets = 0;

    /**
     * 总列数
     */
    private static int totalCells = 0;

    /**
     * 根据文件类型创建对应的Workbook
     *
     * @param fileName    文件名称
     * @param inputStream 文件输入流
     * @return
     */
    private static DataBean<Workbook> createWorkbook(String fileName, InputStream inputStream) {
        DataBean<Workbook> dataBean = new DataBean<>();
        if (fileName == null || "".equals(fileName)) {
            logger.error("<========文件名称不能为空========>");
            dataBean.setData(null);
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("文件名不能为空");
            return dataBean;
        }
        if (fileName.lastIndexOf(".") != -1 ? false : true) {
            logger.error("<========文件名称格式异常，文件名称：{}========>", fileName);
            dataBean.setData(null);
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("文件名格式有误");
            return dataBean;
        }
        if (inputStream == null) {
            logger.error("<========文件输入流为空========>");
            dataBean.setData(null);
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("文件名格式有误");
            return dataBean;
        }
        try (InputStream in = inputStream) {
            if (fileName.toLowerCase().endsWith("xls")) {
                dataBean.setData(new HSSFWorkbook(in));
            }
            if (fileName.toLowerCase().endsWith("xlsx")) {
                dataBean.setData(new XSSFWorkbook(in));
            }
        } catch (IOException e) {
            logger.error("创建Workbook，{}", e.getMessage());
            return dataBean;
        }
        return dataBean;
    }


    /**
     * 获取工作表
     *
     * @param dataBean 工资表数据
     * @param sheetIndex 工资表sheet位置
     * @return
     */
    private static DataBean<Sheet> getSheetByIndex(DataBean<Workbook> dataBean, int sheetIndex) {
        return new DataBean<>(dataBean.getData().getSheetAt(sheetIndex));
    }

    /**
     * 获取行
     *
     * @param dataBean 工资表
     * @param rowIndex 行的位置
     * @return
     */
    private static DataBean<Row> getRowByIndex(DataBean<Sheet> dataBean, int rowIndex) {
        return new DataBean<>(dataBean.getData().getRow(rowIndex));
    }

    /**
     * 获取单元格值
     *
     * @param rowData 一行的数据
     * @param columnIndex 单元格位置
     * @return
     */
    private static DataBean<Object> getCellValue(DataBean<Row> rowData, Integer columnIndex) {
        DataBean<Object> objectData = new DataBean<>();
        Object value = null;
        Cell cell = rowData.getData().getCell(columnIndex);
        if(cell == null){
            objectData.setData(null);
            return objectData;
        }
        switch (cell.getCellType()) {
            case _NONE:
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BLANK:
            case ERROR:
                value = null;
                break;
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    Date theDate = cell.getDateCellValue();
                    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                    value = dff.format(theDate);
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
        }
        objectData.setData(value);
        return objectData;
    }

    /**
     * 读取Excel数据
     *
     * @param fileName 文件名称
     * @param inputStream 输入流
     * @param clazz 范型的Class
     * @param startRow 其实行数
     * @param <T> 范型
     * @return DataBean<List<T>>
     */
    public static <T> DataBean<T> readList(String fileName, InputStream inputStream, Class<T> clazz, int startRow) {
        return baseReadList( fileName,  inputStream, clazz,  startRow,0, 0);
    }

    /**
     * 读取Excel数据
     *
     * @param fileName 文件名称
     * @param inputStream 输入流
     * @param clazz 范型的Class
     * @param startRow 其实行数
     * @param <T> 范型
     * @return DataBean<List<T>>
     * @param sheetsIndex 工资表位置
     */
    public static <T> DataBean<T> readList(String fileName, InputStream inputStream, Class<T> clazz, int startRow, int sheetsIndex) {
        return baseReadList( fileName,  inputStream, clazz,  startRow,0, sheetsIndex);
    }

    /**
     * 读取Excel数据
     *
     * @param fileName 文件名称
     * @param inputStream 输入流
     * @param clazz 范型的Class
     * @param startRow 起始行数位置
     * @param <T> 范型
     * @return DataBean<List<T>>
     * @param sheetsIndex 工资表位置
     * @param endRow 结束行数位置
     */
    public static <T> DataBean<T> readList(String fileName, InputStream inputStream, Class<T> clazz, int startRow, int endRow, int sheetsIndex) {
        return baseReadList( fileName,  inputStream, clazz,  startRow, endRow, sheetsIndex);
    }


    /**
     * 读取Excel数据基类方法
     *
     * @param fileName 文件名称
     * @param inputStream 输入流
     * @param clazz 范型的Class
     * @param startRow 起始行数位置
     * @param <T> 范型
     * @return DataBean<List<T>>
     * @param sheetsIndex 工资表位置
     * @param endRow 结束行数位置
     */
    private static <T> DataBean<T> baseReadList(String fileName, InputStream inputStream, Class<T> clazz, int startRow, int endRow, int sheetsIndex){
        DataBean<T> listData = new DataBean<>();
        /**
         * 数据
         */
        List<T> data = new ArrayList<>();
        DataBean<Workbook> workbookData = createWorkbook(fileName, inputStream);
        if (workbookData.getCode().equals(CodeStatus.FAIL_CODE)) {
            listData.setMsg(workbookData.getMsg());
            return listData;
        }
        /**
         * 获取全部工作表数量
         */
        totalSheets = workbookData.getData().getNumberOfSheets();
        if (totalSheets < 0) {
            listData.setMsg("没有工作表");
            return listData;
        }
        if(endRow<startRow){
            listData.setMsg("结束行位置不能小于起始行位置");
            logger.error("错误的结束行位置{}",endRow);
            return listData;
        }
        try {
            /** 得到第一个sheet */
            DataBean<Sheet> sheetData = getSheetByIndex(workbookData, sheetsIndex);
            if (sheetData.getCode().equals(CodeStatus.FAIL_CODE)) {
                listData.setMsg(sheetData.getMsg());
                return listData;
            }
            /** 得到Excel的行数 */
            totalRows = sheetData.getData().getLastRowNum();
            if (totalRows + 1 < startRow) {
                listData.setMsg("excel没有数据");
                return listData;
            }
            Row titleRow = null;
            // 开始循环row
            for (int i = startRow - 1; i <= totalRows-endRow; i++) {
                /**
                 * 2019-05-27
                 * get title  row
                 */
                if (titleRow == null) {
                    titleRow = getRowByIndex(sheetData, 1).getData();
                }
                T newInstance = clazz.newInstance();
                Row row = getRowByIndex(sheetData, i).getData();
                if (row == null) {
                    continue;
                }
                Boolean flag = false;
                DataBean<Row> rowData = new DataBean<>(row);
                totalCells = rowData.getData().getLastCellNum();
                Field[] fields = clazz.newInstance().getClass().getDeclaredFields();
                for (int j = 0; j < totalCells; j++) {
                    fields[j].setAccessible(true);
                    boolean fieldHasAnno = fields[j].isAnnotationPresent(ExcelField.class);
                    // 有注解
                    if (fieldHasAnno) {
                        ExcelField excelField = fields[j].getAnnotation(ExcelField.class);
                        boolean isNeed = excelField.isNeed();
                        // Excel需要赋值的列
                        if (isNeed) {
                            String beanTitle = excelField.value();
                            for (int k = 0; k <=totalCells; k++) {
                                Object excelTitle = getCellValue(new DataBean<>(titleRow), k);
                                if (excelTitle != null && excelTitle.toString().trim().equals(beanTitle.trim())) {
                                    // 赋值
                                    Object cellValue = getCellValue(rowData, k);
                                    if (cellValue != null && !"".equals(cellValue.toString())) {
                                        flag = true;
                                        Object converterValue = converterValue(cellValue, fields[j]);
                                        fields[j].set(newInstance,converterValue);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if(flag){
                    data.add(newInstance);
                }
            }
            listData.setDataList(data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return listData;
    }


    /**
     * 类型转换
     * @param cellValue 获取的单元格值
     * @param field 需要转换的属性字段
     * @return
     */
    private static Object converterValue(Object cellValue, Field field) {
        Object formatValue = null;
        String type = field.getType().getName();
        switch (type) {
            case "char":
            case "java.lang.Character":
            case "java.lang.String":
                formatValue = cellValue;
                break;
            case "java.util.Date":
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    formatValue = format.parse(cellValue.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "java.lang.Integer":
            case "int":
                formatValue = Integer.valueOf(cellValue.toString());
                break;
            case "float":
            case "java.lang.Float":
                formatValue = Float.valueOf(cellValue.toString());
                break;
            case "double":
            case "java.lang.Double":
                formatValue = Double.valueOf(cellValue.toString());
                break;
            case "java.lang.Long":
            case "long":
                formatValue = Long.valueOf(cellValue.toString());
                break;
            case "java.lang.Short":
            case "short":
                formatValue = Short.valueOf(cellValue.toString());
                break;
            case "java.math.BigDecimal":
                formatValue = new BigDecimal(cellValue.toString());
                break;
            case "java.lang.Boolean":
            case "boolean":
                formatValue = Boolean.valueOf(cellValue.toString());
                break;
        }
        boolean annotationPresent = field.isAnnotationPresent(ExcelField.class);
        if(annotationPresent){
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            boolean isParseScientificNotation = excelField.isParseScientificNotation();
            if(isParseScientificNotation){
                //处理科学计数法
                String value = formatValue.toString();
                if(isENum(value)){
                    DecimalFormat ds = new DecimalFormat("0");
                    value = ds.format(Double.parseDouble(value)).trim();
                }
                formatValue = value;
            }
        }
        return formatValue;
    }


    /*************************************************Excel导出**********************************************************************/


    /**
     * 基础Excel导出
     * @param fileNames 文件名称
     * @param sheetNames sheet名称
     * @param titleNames 标题
     * @param rowNames 列名
     * @param dataList 数据
     * @param type 导出excel类型 03/07
     * @param <T>
     */
    public static <T> DataBean<T> baseExportExcel(List<String> fileNames,List<String> sheetNames,List<String> titleNames,List<List<String>> rowNames,List<List<T>> dataList,ExcelType type){
        DataBean<T> dataBean = new DataBean<>();
        if(!(fileNames != null && !fileNames.isEmpty())){
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("文件名称不能为空:)");
        }
        if(!(sheetNames != null && !sheetNames.isEmpty())){
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("工作簿名称不能为空:)");
        }
        if(!(titleNames != null && !titleNames.isEmpty())){
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("标题不能为空:)");
        }
        if(!(rowNames != null && !rowNames.isEmpty())){
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("列名不能为空:)");
        }
        if(!(dataList != null && !dataList.isEmpty())){
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("数据不能为空:)");
        }
        if(type == null){
            dataBean.setCode(CodeStatus.FAIL_CODE);
            dataBean.setMsg("导出excel文件类型不能为空:)");
        }

        return dataBean;
    }




}
