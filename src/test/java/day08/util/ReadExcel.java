package day08.util;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import day08.pojoinfo.PojoInfoTest;

import java.io.File;
import java.util.List;

public class ReadExcel {
    public static List<PojoInfoTest> getInfo(int sheetnum) {
        File file = new File("src/test/resources/test.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetnum);
        List<PojoInfoTest> pordInfoPojoTest = ExcelImportUtil.importExcel(file, PojoInfoTest.class, importParams);
        return pordInfoPojoTest;
    }

}
