package Adapter;

import Util.XmlUtil;
import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by 10412 on 2016/12/27.
 * JDBC的六大步骤
 * JAVA连接Oracle的三种方式
 */
class DBConnection {
    private static String DataBaseName = XmlUtil.getDataBaseName();
    private static String UserName = XmlUtil.getUserName();
    private static String PassWord = XmlUtil.getPassWord();

    Connection getConnection() throws SQLException {
        Driver driver = new OracleDriver();
        DriverManager.deregisterDriver(driver); // 注册驱动
        Properties pro = new Properties();
        pro.put("user", UserName); // 账号
        pro.put("password", PassWord); // 密码
        return driver.connect("JDBC:oracle:thin:@localhost:1521/" + DataBaseName, pro); //连接数据库
    }
}