package org.shinaikessokuband.anontalk.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

    private String dbUrl="jdbc:mysql://127.0.0.1:3306/onlinechatplatform";
    private String dbUser="root";
    private String dbPwd="WYTKlanni2@";
    private String jdbcName="com.mysql.cj.jdbc.Driver";

    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        return con;
    }

    public void closeCon(Connection con) throws Exception {
        if (con != null){
        con.close();
        }
    }

    public static void main(String[] args) {
        DbUtil dbUtil = new DbUtil();
        try {
            dbUtil.getCon();
            System.out.println("Connection success!");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed!");
        }
    }
}
