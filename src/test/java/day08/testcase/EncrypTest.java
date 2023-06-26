package day08.testcase;

import com.lemon.encryption.RSAManager;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class EncrypTest {
    String orgPassword = "123456";


    @Test
    public void test_md5(){
        //对参数md5加密
//        given().header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8").
//                body("loginame=admin&password="+oMD5Util.stringMD5(orgPassword)).
//                when().post("http://erp.lemfix.com/user/login").then().log().all();
    }
}
