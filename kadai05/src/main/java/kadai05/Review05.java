package kadai05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT * FROM kadaidb.person WHERE id = ?";

            try (
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true", "root",
                            "fake password");

                    PreparedStatement pstmt = con.prepareStatement(sql);) {
                System.out.print("検索キーワードを入力してください > ");
                int input = intKeyIn();

                pstmt.setInt(1, input);
                try (ResultSet rs = pstmt.executeQuery();) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int age = rs.getInt("age");

                        System.out.println(name);
                        System.out.println(age);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }
    }

    private static int intKeyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return Integer.parseInt(line);
    }
}