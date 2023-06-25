package day3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProcessTest {

    @Test
    public void Process() {
        //登录接口
        String loginData = "{\n" +
                "  \"principal\": \"java_auto1\",\n" +
                "  \"credentials\": \"lemon123456\",\n" +
                "  \"appType\": 3,\n" +
                "  \"loginType\": 0\n" +
                "}";
//       Response loginres = given().header("Content-Type","application/json; charset=UTF-8").
//                body(loginData).
//        when().post("http://mall.lemonban.com:8107/login").
//        then().log().all().extract().response();
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("Content-Type", "application/json");
        Response loginres = request("post", "http://mall.lemonban.com:8107/login", loginMap, loginData);
        Object token = loginres.jsonPath().get("access_token");
        System.out.println(token);
        int loginCode = loginres.getStatusCode();
        Assert.assertEquals(loginCode, 200);
        System.out.println(loginCode);
        //商品列表
//        Response searchres = given().queryParam("prodName", "").queryParam("categoryId", "").queryParam("sort", 0).
//                queryParam("orderBy", 0).queryParam("current", 1).queryParam("isAllProdType", true).
//                queryParam("st", 0).queryParam("size", 12).
//                when().get("http://mall.lemonban.com:8107/search/searchProdPage").
//                then().log().all().extract().response();
        Response searchres = request("get", "http://mall.lemonban.com:8107/search/searchProdPage",null, "size=12");
        Object searchProdId = searchres.jsonPath().get("records[0].prodId");
        System.out.println(searchProdId);
        int searchCode = loginres.getStatusCode();
        Assert.assertEquals(searchCode, 200);
        System.out.println(searchCode);
        //商品详情
//        Response detailsres = given().
//                when().get("  http://mall.lemonban.com:8107/prod/prodInfo?prodId=" + searchProdId).
//                then().log().all().extract().response();
        Response detailsres = request("get", "http://mall.lemonban.com:8107/prod/prodInfo", null, "prodId="+searchProdId);
        Object detailsShopId = detailsres.jsonPath().get("shopId");
        System.out.println(detailsShopId);
        Object detailsSkuId = detailsres.jsonPath().get("skuList[0].skuId");
        System.out.println(detailsSkuId);
        int detailsCode = detailsres.getStatusCode();
        Assert.assertEquals(detailsCode, 200);
        System.out.println(detailsCode);
        //加入购物车
        String shopCartData = "{\"basketId\":0,\"count\":1,\"prodId\":\"" + searchProdId + "\",\"shopId\":" + detailsShopId + ",\"skuId\":" + detailsSkuId + "}";
//        Response shopCartres = given().
//                header("Content-Type", "application/json").
//                header("Authorization", "bearer" + token).
//                body(shopCartData).
//                when().post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
//                then().log().all().extract().response();
        Map<String, Object> shopCarthMap = new HashMap<>();
        shopCarthMap.put("Content-Type", "application/json; charset=UTF-8");
        shopCarthMap.put("Authorization", "bearer" + token);
        Response shopCartres = request("post", "http://mall.lemonban.com:8107/p/shopCart/changeItem", shopCarthMap, shopCartData);
        int shopCartCode = shopCartres.getStatusCode();
        Assert.assertEquals(shopCartCode, 200);
        System.out.println(shopCartCode);

        //购物车列表
//        Response shopCartInfores = given().header("Content-Type", "application/json; charset=UTF-8").header("Authorization", "bearer" + token).
//                body("[]").
//                when().post("http://mall.lemonban.com:8107/p/shopCart/info").
//                then().log().all().extract().response();
        Map<String, Object> shopCartInfoMap = new HashMap<>();
        shopCartInfoMap.put("Content-Type", "application/json; charset=UTF-8");
        shopCartInfoMap.put("Authorization", "bearer" + token);
        Response shopCartInfores = request("post", "http://mall.lemonban.com:8107/p/shopCart/info", shopCartInfoMap, "[]");
        Object basketId = shopCartInfores.jsonPath().get("shopCartItemDiscounts[0].shopCartItems[0].basketId[0]");
        System.out.println("basketId：" + basketId);
        int shopCartInfoCode = shopCartInfores.getStatusCode();
        Assert.assertEquals(shopCartInfoCode, 200);
        System.out.println(shopCartInfoCode);
        //选中购物车商品
//        Response totalPayres = given().header("Content-Type", "application/json; charset=UTF-8").header("Authorization", "bearer" + token).
//                body("[\n" +
//                        "  3512\n" +
//                        "]").
//                when().post("http://mall.lemonban.com:8107/p/shopCart/totalPay").
//                then().log().all().extract().response();
        Map<String, Object> totalPayoMap = new HashMap<>();
        totalPayoMap.put("Content-Type", "application/json; charset=UTF-8");
        totalPayoMap.put("Authorization", "bearer" + token);
        Response totalPayres = request("post", "http://mall.lemonban.com:8107/p/shopCart/totalPay", totalPayoMap, "[\n" +
                "  3512\n" +
                "]");
        int totalPayCode = totalPayres.getStatusCode();
        Assert.assertEquals(totalPayCode, 200);
        System.out.println(totalPayCode);
        //提交订单
        String confirmData = "{\n" +
                "  \"addrId\": 0,\n" +
                "  \"basketIds\": [\n" +
                "    " + basketId + "\n" +
                "  ],\n" +
                "  \"couponIds\": [],\n" +
                "  \"isScorePay\": 0,\n" +
                "  \"userChangeCoupon\": 0,\n" +
                "  \"userUseScore\": 0,\n" +
                "  \"uuid\": \"108f94d8-a25c-4012-8c34-5cd25b18afb1\"\n" +
                "}";
//        Response confirmres = given().header("Content-Type", "application/json; charset=UTF-8").header("Authorization", "bearer" + token).
//                body(confirmData).
//                when().post("http://mall.lemonban.com:8107/p/order/confirm").
//                then().log().all().extract().response();
        Map<String, Object> confirmMap = new HashMap<>();
        confirmMap.put("Content-Type", "application/json; charset=UTF-8");
        confirmMap.put("Authorization", "bearer" + token);
        Response confirmres = request("post", "http://mall.lemonban.com:8107/p/order/confirm", confirmMap, confirmData);
        int confirmCode = confirmres.getStatusCode();
        Assert.assertEquals(confirmCode, 200);
        System.out.println(confirmCode);
        //确认订单
        String submitData = "{\n" +
                "  \"orderShopParam\": [\n" +
                "    {\n" +
                "      \"remarks\": \"\",\n" +
                "      \"shopId\": " + detailsShopId + "\n" +
                "    }\n" +
                "  ],\n" +
                "  \"uuid\": \"502ffc4c-f582-4218-8912-304486f3651c\"\n" +
                "}";
        Map<String, Object> submitMap = new HashMap();
        submitMap.put("Content-Type", "application/json; charset=UTF-8");
        submitMap.put("Authorization", "bearer" + token);
        Response submitres = request("post", "http://mall.lemonban.com:8107/p/order/submit", submitMap, submitData);
//        Response submitres = given().headers(headeMap).
//                body(submitData).
//                when().post("http://mall.lemonban.com:8107/p/order/submit").
//                then().log().all().extract().response();
        int submitCode = submitres.getStatusCode();
        Assert.assertEquals(submitCode, 200);
        System.out.println(submitCode);


    }

    //封装请求参数信息
    public Response request(String method, String url, Map<String, Object> map, String params) {
        Response res = null;
        if ("post".equals(method)) {
            res = given().log().all().headers(map).body(params).
                    when().post(url).
                    then().log().all().extract().response();
        } else if ("get".equals(method)) {
            res = given().log().all().when().get(url +"?"+ params).then().log().all().extract().response();
        } else if ("put".equals(method)) {
            res = given().headers(map).body(params).when().put(url).then().log().all().extract().response();
        } else if ("delete".equals(method)) {
            res = given().headers(map).body(params).when().delete(url).then().log().all().extract().response();
        }
        return res;
    }


}
