package day06.common;

import com.alibaba.fastjson.JSONObject;
import day06.data.Environment;
import day06.pojoinfo.PojoInfoTest;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;


//封装request请求
public class BaseTest {
    //封装request
    public Response request(PojoInfoTest pojoInfoTest) {
        String method = pojoInfoTest.getMethod();
        String url = pojoInfoTest.getUrl();
        String headers = pojoInfoTest.getHeaders();
        String params = pojoInfoTest.getParams();
        headers = replaceParam(headers);
        params = replaceParam(params);
        Map<String, Object> header = JSONObject.parseObject(headers);
        Response res = null;
        if ("post".equals(method)) {
            if (header.containsValue("multipart/form-data")){
                res = given().log().all().headers(header).multiPart(new File(params)).when().post(url).then().log().all().extract().response();
            }else {
                res = given().log().all().headers(header).body(params).when().post(url).then().log().all().extract().response();
            }
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

    //封装断言
    public void assertResponse(PojoInfoTest loginPojo, Response res) {
        if (loginPojo.getExpectValues() != null) {
            //获取excel的期望结果
            String expectValues = loginPojo.getExpectValues();
            //转为map，方便取值
            Map<String, Object> expectValuesMap = JSONObject.parseObject(expectValues);
            Set<String> AllKey = expectValuesMap.keySet();
            for (String key : AllKey) {
                System.out.println(key+"dddddddddddddddddddddd");
                if (key.equals("code")) {
                    //比较code值
                    Assert.assertEquals(res.getStatusCode(), expectValuesMap.get(key));
                } else if (key.equals("bobystring")) {
                    //比较返回的文本信息
                    Assert.assertEquals(res.body().asString(), expectValuesMap.get(key));
                } else {
                    //比较响应体的某个字段值
//                res.jsonPath().get(key);
//                expectValuesMap.get(key);
                    Assert.assertEquals(res.jsonPath().get(key), expectValuesMap.get(key));
                }
            }
        }
    }

    //提取响应字段
    public static void extractValues(PojoInfoTest pojoInfoTest, Response request) {
        if (pojoInfoTest.getExtractValues() != null) {
            String extractValues = pojoInfoTest.getExtractValues();
            Map<String, Object> actualValuesMap = JSONObject.parseObject(extractValues);
            Set<String> AllKey = actualValuesMap.keySet();
            for (String key : AllKey) {
                actualValuesMap.get(key);
                System.out.println(key+"====="+actualValuesMap.get(key)+"#####################");
                Object values = request.jsonPath().get(actualValuesMap.get(key)+"");
                Environment.var.put(key, values);
            }
        }
    }

    //替换
    public static String replaceParam(String orgStr) {
        if (orgStr != null) {
            Pattern pattern = Pattern.compile("#(.*?)#");
            Matcher matcher = pattern.matcher(orgStr);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = Environment.var.get(key) + "";
                orgStr = orgStr.replace(matcher.group(0), value);
                System.out.println(orgStr);
            }

        }
        return orgStr;
    }
//    public static void main(String[] args) {
//        String orgStr="{\n" +
//                "    \"basketId\": 0,\n" +
//                "    \"count\": 1,\n" +
//                "    \"prodId\": \"#prodId#\",\n" +
//                "    \"shopId\": #shopId#,\n" +
//                "    \"skuId\": #skuId#\n" +
//                "}";
//        Environment.var.put("prodId",200);
//        Environment.var.put("shopId",300);
//        Environment.var.put("skuId",400);
//        replaceParam(orgStr);
//    }
}
