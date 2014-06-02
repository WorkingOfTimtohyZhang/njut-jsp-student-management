package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 数据库连接类
 *
 * @author 邓子云
 */
public class dbconn {
    String driverName = null;//数据库驱动名
    String connString = null;//连接字符串
    String userName = null;//用户名
    String password = null;//密码
    String propertyFileName = null;//.properties文件名称

    public dbconn() {
    }

    public Connection getDBConn() {//得到数据库连接对象
        driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        connString = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=chengji";
        userName = "sa";
        password = "";
        if (driverName == null || connString == null || userName == null) return null;
        try {
            Connection connDBObject = null;
            Class.forName(driverName);
            return DriverManager.getConnection(connString, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
            return null;
        }
    }

    public String getConnString() {
        return connString;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPropertyFileName(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    public String getPropertyFileName() {
        return propertyFileName;
    }

    public String getPropertyFromFile(String refName) {//从.properties文件中得到refName属性的值
        if (this.getPropertyFileName() == null) return new String("");
        try {
            InputStream fin = getClass().getResourceAsStream(this.getPropertyFileName());
            Properties props = new Properties();
            props.load(fin);
            return (String) props.getProperty(refName);
        } catch (Exception e) {
            e.printStackTrace();
            return new String("");
        }
    }
}
