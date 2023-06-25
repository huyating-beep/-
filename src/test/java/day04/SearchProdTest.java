package day04;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SearchProdTest {
    @Test(dataProvider = "SearchProdData")
    public void SearchProd(ExcelPojo excelPojo) {

        Response res = request(excelPojo.getMethod(), excelPojo.getUrl(),null , excelPojo.getParams());
        System.out.println("地址为："+excelPojo.getUrl()+ excelPojo.getParams());
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @DataProvider
    public Object[] SearchProdData() {
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(1);
        File file = new File("src/test/resources/test.xlsx");
        List<ExcelPojo> excelPojos = ExcelImportUtil.importExcel(file, ExcelPojo.class, importParams);
        System.out.println(excelPojos);
        return excelPojos.toArray();
    }

    public Response request(String method, String url, Map<String, Object> headerMap, String params) {
        Response res = null;
        if ("post".equals(method)) {
            res = given().log().all().headers(headerMap).body(params).when().post(url).then().log().all().extract().response();
        } else if ("get".equals(method)) {
            if (headerMap==null){
                res = given().log().all().when().get(url + "?" + params).then().log().all().extract().response();
            }else {
                res = given().log().all().headers(headerMap).when().get(url + "?" + params).then().log().all().extract().response();
            }
        } else if ("put".equals(method)) {
            res = given().log().all().headers(headerMap).body(params).when().put(url).then().log().all().extract().response();
        } else if ("delete".equals(method)) {
            res = given().log().all().headers(headerMap).body(params).when().delete(url).then().log().all().extract().response();
        }
        return res;
    }
//    @Test
//    public void formTest(){
//        Map<String,Object> map = new HashMap();
//        map.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//       Response res = request("post","http://erp.lemfix.com/user/login",map,"loginame=admin&password=e10adc3949ba59abbe56e057f20f883e");
//    }
    //post请求  multipart传参
//    @Test
//    public void multipartTest(){
//        Map<String,Object> map = new HashMap();
//        map.put("Content-Type","multipart/form-data; boundary=----WebKitFormBoundaryvIkuJAuWGbV8tga5");
//        File file1=new File("/Users/huyating/Desktop/test2.png");
//        String file = file1.toString();
//        Response res = request("post","https://uploaderv51.ketangpai.com/UploadApi/uploadAvatarByCrop?token=b690811c1514f8458d3729c521db20c575b5b7f71995fb4acb5608be8120ffce",map,file);
//    }
}
