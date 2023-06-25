package day06.testcase;


import day06.common.BaseTest;
import day06.excelutil.ReadExcel;
import day06.pojoinfo.PojoInfoTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UploadPcture extends BaseTest {
    @Test(dataProvider = "getInfo")
    public void ploadPcture(PojoInfoTest pojoInfoTest){
        Response request = request(pojoInfoTest);
        assertResponse(pojoInfoTest,request);
        extractValues(pojoInfoTest,request);
    }
    @DataProvider
    public Object[] getInfo(){
        return ReadExcel.getInfo(4).toArray();
    }
}
