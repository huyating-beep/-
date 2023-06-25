package day08.testcase;

import com.github.javafaker.Faker;
import day08.common.BaseTest;
import day08.data.Environment;
import day08.pojoinfo.PojoInfoTest;
import day08.util.DBUtil;
import day08.util.RandomUtil;
import day08.util.ReadExcel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Locale;

public class RegTest extends BaseTest {
    String iphone;
    String name;
    @BeforeClass
    public void setUp() {
         iphone = RandomUtil.RandomRegIphone();
         name = RandomUtil.RandomRegName();
        Environment.var1.put("mobile", iphone);
        Environment.var1.put("userName", name);
    }

    @Test(dataProvider = "getRegData")
    public void Reg(PojoInfoTest pojoInfoTest) {
        //获取请求
        Response request = request(pojoInfoTest);
        if (pojoInfoTest.getCaseid() == 1) {
            Object data = DBUtil.QuerySingData("select mobile_code from tz_sms_log where user_phone ='"+iphone+"' order by rec_date desc limit 1");
            Environment.var1.put("validCode", data);
        }
        //断言
        assertResponse(pojoInfoTest, request);
        //提取参数字段
        extractValues(pojoInfoTest, request);
        //数据库断言
        asserDB(pojoInfoTest);
    }

    @DataProvider
    public Object[] getRegData() {
        return ReadExcel.getInfo(6).toArray();
    }

}
