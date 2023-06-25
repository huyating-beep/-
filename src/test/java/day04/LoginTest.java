package day04;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.xmlbeans.impl.store.Public2;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginTest {
    @Test(dataProvider = "loginDataProvider")
    public void login(ExcelPojo excelPojo) {
        String method = excelPojo.getMethod();
        String headers = excelPojo.getHeaders();
        Map<String,Object> headerMap = JSONObject.parseObject(headers);
        String url = excelPojo.getUrl();
        String params = excelPojo.getParams();
        Response res = request(method, headerMap, url, params);
        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
    @DataProvider
    public Object[] loginDataProvider(){
        File file = new File("src/test/resources/test.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        List<ExcelPojo> excelList = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        System.out.println(excelList);
        return excelList.toArray();
    }


    public Response request(String method, Map<String, Object> map, String url, String params) {
        Response res = null;
        if ("post".equals(method)) {
            res = given().log().all().headers(map).body(params).when().post(url).then().log().all().extract().response();
        } else if ("get".equals(method)) {
            res = given().log().all().when().get(url + "?" + params).then().log().all().extract().response();
        } else if ("put".equals(method)) {
            res = given().log().all().headers(map).body(params).when().put(url).then().log().all().extract().response();
        } else if ("delete".equals(method)) {
            res = given().log().all().headers(map).body(params).when().delete(url).then().log().all().extract().response();
        }
        return res;
    }

}
