package day1;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class RestassuredTest {
    @Test
    public void login(){
        //post请求  json传参

        String data="{\"principal\":\"java_auto1\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        //设置请求头，请求体
        given().header("Content-Type","application/json; charset=utf-8")
                .body(data).
        //设置请求方式
        when().post("http://mall.lemonban.com:8107/login").
        //设置断言，日志
        then().log().all();
    }
    //post请求  form传参
    @Test
    public void formTest(){
        String dataFrom="http://erp.lemfix.com/user/login";
        given().header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8").
                formParam("loginame","admin").formParam("password","e10adc3949ba59abbe56e057f20f883e").
        when().post(dataFrom).
        then().log().all();
    }
    //post请求  multipart传参
    @Test
    public void multipartTest(){
        given().header("Content-Type","multipart/form-data; boundary=----WebKitFormBoundaryvIkuJAuWGbV8tga5").
                multiPart(new File("/Users/huyating/Desktop/test2.png")).
        when().post("https://uploaderv51.ketangpai.com/UploadApi/uploadAvatarByCrop?token=b690811c1514f8458d3729c521db20c575b5b7f71995fb4acb5608be8120ffce").
        then().log().body();
    }




    //get请求1
    @Test
    public void searchList(){
        given().
        when().get("http://mall.lemonban.com:8107/search/searchProdPage?prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12").
        then().log().all();
    }
    //get请求2
    @Test
    public void searchList2(){
        given().queryParam("prodName","").queryParam("categoryId","").queryParam("sort",0).
                queryParam("orderBy",0).queryParam("current",1).queryParam("isAllProdType",true).
                queryParam("st",0).queryParam("size",12).
        when().get("http://mall.lemonban.com:8107/search/searchProdPage").
        then().log().all();
    }


}
