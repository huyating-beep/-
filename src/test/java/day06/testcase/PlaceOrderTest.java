package day06.testcase;

import com.alibaba.fastjson.JSONObject;
import day06.common.BaseTest;
import day06.excelutil.ReadExcel;
import day06.pojoinfo.PojoInfoTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;


public class PlaceOrderTest extends BaseTest {
    //执行login用例
    @Test(dataProvider = "getPlaceOrder")
    public void PlaceOrder(PojoInfoTest pojoInfoTest) {

        Response request = request(pojoInfoTest);
       //断言
        assertResponse(pojoInfoTest,request);
        //提取响应字段
        extractValues(pojoInfoTest,request);
    }

    //获取excel数据信息
    @DataProvider
    public Object[] getPlaceOrder() {
        return ReadExcel.getInfo(3).toArray();
    }


}


