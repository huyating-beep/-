package day08.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBUtil {
    public static Connection getConnection() {
        //定义数据库连接
        String url = "jdbc:mysql://47.113.180.81/yami_shops?useUnicode=true&characterEncoding=utf-8&useSSL=true";
        String user = "lemon";
        String password = "lemon123";
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    //单个数据信息
    public static Object QuerySingData(String sql) {
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        Object query = null;
        try {
            query = queryRunner.query(connection, sql, new ScalarHandler<Object>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    //查询多个数据信息
    public static List<Map<String, Object>> QueryAllData(String sql) {
        Connection connection = getConnection();
        QueryRunner queryRunner = new QueryRunner();
        List<Map<String, Object>> query = null;
        try {
            query = queryRunner.query(connection, sql, new MapListHandler());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;

    }


//    public static void main(String[] args) throws SQLException {
//        //1.连接数据库
//        Connection connection = getConnection();
//        //2、操作数据库
//        //执行查询sql语句，获取数据单个信息
//        QueryRunner queryRunner =new QueryRunner();
//        Long query = queryRunner.query(connection, "select count(*) from tz_order", new ScalarHandler<Long>());
//        System.out.println("查询单个数据信息"+query);
//        //执行查询sql语句，获取数据第一条信息
//        Map<String, Object> query1 = queryRunner.query(connection, "select * from tz_order", new MapHandler());
//        System.out.println("获取数据第一条信息"+query1);
//        //执行查询sql语句，获取数据符合条件的数据
//        List<Map<String, Object>> query2 = queryRunner.query(connection, "select * from tz_order where total >1000", new MapListHandler());
//        for (Map<String, Object> map : query2) {
//            System.out.println(map.get("order_number"));
//
//        }
//        //增加一条数据信息
//        queryRunner.update(connection,"insert into tz_order value ('10000','1','测试专用','f4d024ae5e004273850c13bd926d6507','3')");
//        //删除一条数据信息
//        queryRunner.update(connection,"delete from tz_order where order_id=2");
//        //修改一条数据信息
//        queryRunner.update(connection,"update tz_order set order_number = 1000 where order_id=2;");
//    }
}
