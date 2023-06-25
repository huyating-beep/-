package day08.util;

import com.github.javafaker.Faker;

import java.util.Locale;

public class RandomUtil {
    //获取一个随机的手机号
    public static String RandomRegIphone() {
        Faker faker = new Faker(Locale.CHINA);
        String randomPhone;
        Object result;
        while (true) {
            randomPhone = faker.phoneNumber().cellPhone();
            result = DBUtil.QuerySingData("select count(*) from tz_user where user_mobile='" + randomPhone + "'");
            if ((Long) result == 1) {
                continue;
            } else {
                break;

            }
        }
        return randomPhone;
    }
    //获取一个随机的姓名

    public static String RandomRegName() {
        Faker faker = new Faker();
        String randomName;
        Object result;
        while (true) {
            randomName = faker.name().firstName();
            result = DBUtil.QuerySingData("select count(*) from tz_user where user_name='" + randomName + "'");
            if ((Long) result == 1) {
                continue;
            } else if (randomName.length() >= 4 || randomName.length() <= 16) {
                break;
            }

        }
        return randomName;
    }

}
