package com.mifa.cloud.voice.server.utils;

import com.alibaba.fastjson.JSON;
import com.mifa.cloud.voice.server.commons.constants.AppConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
public class OperExcel {
    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010 模板必须是预先给的文本类型的
     */
    public static List<Map<String,Object>> readExcel(String filePath, String[] metaName)
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            //同时支持Excel 2003、2007
            File excelFile = new File(filePath);
            if (!excelFile.exists()) {
                log.warn("路径不存在,任务结束");
                return Collections.emptyList();
            }
            FileInputStream is = new FileInputStream(excelFile);
            //这种方式 Excel 2003/2007/2010 都是可以处理的
            Workbook workbook = WorkbookFactory.create(is);
            //Sheet的数量
            int sheetCount = workbook.getNumberOfSheets();
            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                //获取总行数
                int rowCount = sheet.getPhysicalNumberOfRows();
                //遍历每一行 //跳过第一行
                for (int r = 1; r < rowCount; r++) {
                    Map<String,Object> map = new HashMap<>();
                    Row row = sheet.getRow(r);
                    //获取总列数
                    int cellCount = row.getPhysicalNumberOfCells();
                    //遍历每一列
                    for (int c = 0; c < cellCount; c++) {
                        Cell cell = row.getCell(c);
                        int cellType = cell.getCellType();
                        String cellValue = null;
                        switch(cellType) {
                            case Cell.CELL_TYPE_STRING: //文本
                                cellValue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC: //数字、日期
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = fmt.format(cell.getDateCellValue()); //日期型
                                }
                                else {
                                    DecimalFormat df = new DecimalFormat("0");
                                    cellValue = String.valueOf(df.format(cell.getNumericCellValue())); //数字
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN: //布尔型
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_BLANK: //空白
                                cellValue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_ERROR: //错误
                                cellValue = "错误";
                                break;
                            case Cell.CELL_TYPE_FORMULA: //公式
                                cellValue = "错误";
                                break;
                            default:
                                cellValue = "错误";
                        }
                        map.put(metaName[c],cellValue);
                        System.out.print(cellValue + "    ");
                    }
                    list.add(map);
                    System.out.println();
                }
            }
        }
        catch (Exception e) {
           log.error("工具类异常:{}",e);
        }
        return list;
    }

    public static void main(String[] args) {
       List<Map<String,Object>> list = readExcel("/Users/sxm/Desktop/voice_contact_template.xlsx", AppConst.VOICE_TEMPLATE_METAS);
        System.out.println(JSON.toJSONString(list));
    }
}
