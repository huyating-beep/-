package day2;

import io.restassured.http.Headers;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ExtractResponseTest {
    //柠檬班
    @Test
    public void login(){
        String data="{\"principal\":\"java_auto1\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        Response res = given().header("Content-Type", "application/json; charset=utf-8").
                body(data).
                when().post("http://mall.lemonban.com:8107/login").
                then().log().all().extract().response();
        int statusCode = res.getStatusCode();
        System.out.println("响应码："+statusCode);
        long time = res.getTime();
        System.out.println("响应时间："+time);
        String body = res.getBody().asString();
        System.out.println("响应体："+body);
        String contentType = res.getContentType();
        System.out.println("请求类型："+contentType);
        Headers headers = res.getHeaders();
        String cookie = res.getHeader("Set-Cookie");
        System.out.println("响应头单个信息："+cookie);
        System.out.println("响应头："+headers);

        //获取请求体里某个字段
        String accessToken = res.jsonPath().get("access_token").toString();
        System.out.println("token："+accessToken);
    }
    @Test
    public void searchList2(){
        Response searResponse = given().queryParam("prodName", "").queryParam("categoryId", "").queryParam("sort", 0).
                queryParam("orderBy", 0).queryParam("current", 1).queryParam("isAllProdType", true).
                queryParam("st", 0).queryParam("size", 12).
                when().get("http://mall.lemonban.com:8107/search/searchProdPage").
                then().log().all().extract().response();
        Object prodId = searResponse.jsonPath().getString("records[0].prodId");
        System.out.println("prodId:"+prodId);
        Object price = searResponse.jsonPath().get("records.findAll{it.price = 0.01&&it.prodName=='寄到家'}.prodId");
        System.out.println(price);
    }
    @Test
    public void shopCart(){
        Response res = given().header("Content-Type", "application/json;").header("Authorization","bearer9e08a039-9078-4338-870b-56b9928f881c").
                body("[]").
                when().post("http://mall.lemonban.com:8107/p/shopCart/info").
                then().log().body().extract().response();
        Object prodName = res.jsonPath().get("shopCartItemDiscounts[0].shopCartItems[0].prodName[2]");
        System.out.println("prodName："+prodName);
    }
    //登录断言
    @Test
    public void loginAssertion(){
        String data="{\"principal\":\"java_auto1\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        Response res = given().header("Content-Type", "application/json; charset=utf-8").
                body(data).
                when().post("http://mall.lemonban.com:8107/login").
                then().log().all().extract().response();
        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode,200);
        System.out.println("响应码："+statusCode);
        Object nickName=res.jsonPath().get("nickName");
        Assert.assertEquals(nickName,"java_auto1");
        System.out.println(nickName);


    }


//课堂派
    @Test
    public void loginketangpai(){
        String data = "{\n" +
                "  \"email\": \"17611176928\",\n" +
                "  \"password\": \"hyt928464...\",\n" +
                "  \"remember\": \"0\",\n" +
                "  \"code\": \"\",\n" +
                "  \"mobile\": \"\",\n" +
                "  \"type\": \"login\",\n" +
                "  \"reqtimestamp\": 1680428641095\n" +
                "}";
        Response res = given().header("Content-Type", "application/json; charset=utf-8").
                body(data).
                when().post("https://openapiv5.ketangpai.com//UserApi/login").
                then().log().body().extract().response();
        Object tonken = res.jsonPath().get("data.token");
        System.out.println("tonken："+tonken);
    }
    @Test
    public void multipartTest(){
        given().header("Content-Type","multipart/form-data; boundary=----WebKitFormBoundaryvIkuJAuWGbV8tga5").
                multiPart(new File("/Users/huyating/Desktop/test2.png")).
                when().post("https://uploaderv51.ketangpai.com/UploadApi/uploadAvatarByCrop?token="+"tonken").
                then().log().body();
    }

    //百度网页html解析
    @Test
    public void baidu(){
        Response res = given().
                when().get("https://www.baidu.com").
                then().log().all().extract().response();
        Object title = res.htmlPath().get("html.head.title");
        System.out.println(title);
        Object name = res.htmlPath().get("html.head.meta[2].@name");
        System.out.println(name);
    }

}
