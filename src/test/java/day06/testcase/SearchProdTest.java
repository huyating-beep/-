package day06.testcase;

import com.alibaba.fastjson.JSONObject;
import day06.common.BaseTest;
import day06.excelutil.ReadExcel;
import day06.pojoinfo.PojoInfoTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class SearchProdTest extends BaseTest {
    @Test(dataProvider = "SearchProdData")
    public void SearchProd(PojoInfoTest pojoInfoTest) {
        Response res = request(pojoInfoTest);
        assertResponse(pojoInfoTest,res);
    }

    @DataProvider
    public Object[] SearchProdData() {
        return ReadExcel.getInfo(1).toArray();
    }
}
