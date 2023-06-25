package day08.testcase;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.common.BaseTest;
import test.excelutil.ReadExcel;
import test.pojoinfo.PojoInfoTest;

public class UploadPicture extends BaseTest {
    @Test(dataProvider = "getDatas")
    public void uploadPicture(PojoInfoTest pojoInfoTest) {
        //解析接口
        Response request = request(pojoInfoTest);
        //断言
         assertResponse(pojoInfoTest, request);
        //提取参数信息
         extractValues(pojoInfoTest, request);
    }
    @DataProvider
    public Object[] getDatas() {
        return ReadExcel.getInfo(4).toArray();
    }


}
