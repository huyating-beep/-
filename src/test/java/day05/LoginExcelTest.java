package day05;

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


public class LoginExcelTest {
    //执行login用例
    @Test(dataProvider = "getLogin")
    public void Login(LoginPojo loginPojo) {
        String method = loginPojo.getMethod();
        System.out.println("--------------");
        System.out.println(method);
        String url = loginPojo.getUrl();
        String headers = loginPojo.getHeaders();
        Map<String, Object> header = JSONObject.parseObject(headers);
        String params = loginPojo.getParams();
        Response request = request(method, url, header, params);
        int statusCode = request.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }


    //获取excel数据信息
    @DataProvider
    public Object[] getLogin() {
        File file = new File("src/test/resources/test.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        //设置读取的行数
//        importParams.setReadRows(1);
        List<LoginPojo> loginPojoTest = ExcelImportUtil.importExcel(file, LoginPojo.class, importParams);
        return loginPojoTest.toArray();
    }

    //封装request请求
    public Response request(String method, String url, Map<String, Object> header, String params) {
        Response res = null;
        if ("post".equals(method)) {
            res = given().log().all().headers(header).body(params).when().post(url).then().log().all().extract().response();
        } else if ("get".equals(method)) {
            if (header == null) {
                res = given().log().all().when().get(url + "?" + params).then().log().all().extract().response();
            } else {
                res = given().log().all().headers(header).when().get(url + "?" + params).then().log().all().extract().response();
            }
        } else if ("put".equals(method)) {
            res = given().log().all().headers(header).body(params).when().put(url).then().log().all().extract().response();
        } else if ("delete".equals(method)) {
            res = given().log().all().headers(header).body(params).when().delete(url).then().log().all().extract().response();
        }
        return res;
    }
    @Test
    public void searchList(){
        request("get","http://mall.lemonban.com:8107/search/searchProdPage?",null,"prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12");
    }

    @Test
    public void formTest(){
        Map<String,Object> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        request("post","http://erp.lemfix.com/user/login",mapHeader,"loginame=admin&password=e10adc3949ba59abbe56e057f20f883e");
    }
}


