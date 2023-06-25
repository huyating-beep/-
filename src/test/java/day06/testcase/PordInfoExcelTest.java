package day06.testcase;

import com.alibaba.fastjson.JSONObject;
import day06.common.BaseTest;
import day06.pojoinfo.PojoInfoTest;
import day06.excelutil.ReadExcel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.Set;


public class PordInfoExcelTest extends BaseTest {
    //执行商品详情用例
    @Test(dataProvider = "getPordInfo")
    public void pordInfo(PojoInfoTest pojoInfoTest) {
        Response request = request(pojoInfoTest);
        //断言
        assertResponse(pojoInfoTest,request);
    }



    //获取excel数据信息
    @DataProvider
    public Object[] getPordInfo() {
        return ReadExcel.getInfo(2).toArray();
    }

}


