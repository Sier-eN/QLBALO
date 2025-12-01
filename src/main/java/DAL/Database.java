package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
      + "databaseName=QuanLyBaloCapSach;"
      + "encrypt=false;"
      + "trustServerCertificate=true;"
      + "instanceName=MSSQLSERVER01;";

    private static final String USER = "sa";
    private static final String PASS = "123"; // đổi theo mật khẩu SQL Server của bạn

    public static Connection getConnection() {
        Connection ketnoi = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            ketnoi = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Ket noi thanh cong");

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay Driver JDBC SQL Server");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("Loi ket noi SQL Server: " + e.getMessage());
            e.printStackTrace();
        }

        return ketnoi;
    }

    public static void main(String[] args) {
        Connection test = getConnection();
        if (test != null) {
            try {
                test.close();
                System.out.println("Da dong ket noi");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
