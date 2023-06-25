package day08.common;

import com.alibaba.fastjson.JSONObject;
import day08.data.Environment;
import day08.pojoinfo.PojoInfoTest;
import day08.util.DBUtil;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseTest {
    //封装request请求
    public static Response request(PojoInfoTest pojoInfoTest) {
        String method = pojoInfoTest.getMethod();
        String url = pojoInfoTest.getUrl();
        String headers = pojoInfoTest.getHeaders();
        String params = pojoInfoTest.getParams();
        url = replaceParam(url);
        headers = replaceParam(headers);
        params = replaceParam(params);
        Map<String, Object> header = JSONObject.parseObject(headers);
        Response res = null;
        if ("post".equals(method)) {
            if (header.containsValue("multipart/form-data")) {
                res = given().headers(header).multiPart(new File(params)).log().all().when().post(url).then().log().all().extract().response();
            } else {
                res = given().headers(header).body(params).log().all().when().post(url).then().log().all().extract().response();
            }
        } else if ("get".equals(method)) {
            if (header == null) {
                res = given().log().all().get(url + "?" + params).then().log().all().extract().response();
            } else {
                res = given().log().all().headers(header).get(url + "?" + params).then().log().all().extract().response();
            }
        } else if ("put".equals(method)) {
            res = given().headers(header).body(params).log().all().when().put(url).then().log().all().extract().response();
        } else if ("delete".equals(method)) {
            res = given().headers(header).body(params).log().all().when().delete(url).then().log().all().extract().response();
        }
        return res;
    }


    //提取响应字段
    public static void extractValues(PojoInfoTest pojoInfoTest, Response request) {
        String extractValues = pojoInfoTest.getExtractValues();
        if (extractValues != null) {
            Map<String, Object> actualValuesMap = JSONObject.parseObject(extractValues);
            Set<String> AllKey = actualValuesMap.keySet();
            for (String key : AllKey) {
                //拿到所有的value
                Object value = actualValuesMap.get(key);
                if (value.equals("bobystring")) {
                    String valueString = request.body().asString();
                    Environment.var1.put(key,valueString);
                } else {
                    Object values = request.jsonPath().get(value + "");
                    Environment.var1.put(key, values);
                }
            }
        }
    }

    //替换
    public static String replaceParam(String orgStr1) {
        if (orgStr1 != null) {
            Pattern pattern = Pattern.compile("#(.*?)#");
            Matcher matcher = pattern.matcher(orgStr1);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = Environment.var1.get(key) + "";
                orgStr1 = orgStr1.replace(matcher.group(0), value);
            }

        }
        return orgStr1;
    }

    //实际值与期望结果封装断言
    public void assertResponse(PojoInfoTest pojoInfoTest, Response res) {
        if (pojoInfoTest.getExpectValues() != null) {
            String expectValues = pojoInfoTest.getExpectValues();
            Map<String, Object> expectValuesMap = JSONObject.parseObject(expectValues);
            Set<String> AllKey = expectValuesMap.keySet();
            for (String key : AllKey) {
                if (key.equals("code")) {
                    //比较code值
                    Assert.assertEquals(res.getStatusCode(), expectValuesMap.get(key));
                } else if (key.equals("bobystring")) {
                    Assert.assertEquals(res.body().asString(), expectValuesMap.get(key));
                } else {
                    Assert.assertEquals(res.jsonPath().get(key), expectValuesMap.get(key));
                }
            }
        }
    }
    //期望值与数据库结果封装断言
    public void asserDB(PojoInfoTest pojoInfoTest){
        String actualValues = pojoInfoTest.getActualValues();
        if (actualValues!=null){
            actualValues = replaceParam(actualValues);
            Map<String,Object> actualValuesMap = JSONObject.parseObject(actualValues);
            Set<String> AllKey = actualValuesMap.keySet();
            for (String key : AllKey) {
                Object dbKey = DBUtil.QuerySingData(key)+"";
                Object values =  actualValuesMap.get(key);
                Assert.assertEquals(dbKey,values);
            }
        }
    }
}
