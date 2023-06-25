package day08.testcase;


import day08.common.BaseTest;
import day08.data.Environment;
import day08.pojoinfo.PojoInfoTest;
import day08.util.DBUtil;
import day08.util.ReadExcel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {
    @BeforeClass
    public void setup(){
        Object data = DBUtil.QuerySingData("select t1.prod_name from tz_prod t1,tz_sku t2 where t1.prod_id=t2.prod_id and t1.status=1 and t1.seckill_activity_id = 0 and t1.group_activity_id =0 and t2.stocks>20 limit 1");
        Environment.var1.put("prodName",data);
    }
    @Test(dataProvider = "getPlaceOrder")
    public void PlaceOrder(PojoInfoTest pojoInfoTest) {
        //请求接口数据信息
        Response request = BaseTest.request(pojoInfoTest);
        //断言
       assertResponse(pojoInfoTest, request);
        //提取数据信息
        extractValues(pojoInfoTest, request);
    }
    //通过数据驱动读取excel的数据信息
    @DataProvider
    public Object[] getPlaceOrder() {
        return ReadExcel.getInfo(3).toArray();
    }
}
