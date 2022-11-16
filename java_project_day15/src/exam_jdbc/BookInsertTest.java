package exam_jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BookInsertTest {
    public static void main(String[] args) {
        addBook("자바의 정석 ","남궁석","2000",30000);
    }

    //인서트 메소드만들기
    //Statement 로 사용해보기.
    private static void addBook(String title, String pulisher, String year, int price) {
        Connection con = ConnectDatabase.makeConnection("javauser", "java1234");
        Statement stmt = null;
        try {
            stmt = con.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO book (book_id, title, publisher, year, price) VALUES (book_seq.nextval, ");
            sb.append("'" + title + "','" + pulisher + "','" + year + "'," + price + ")");

            System.out.println("쿼리문 확인 : " + sb);

            int insertCount = stmt.executeUpdate(sb.toString());
            if (insertCount == 1) {
                System.out.println("레코드 추가 성공");
            } else {
                System.out.println("레코드 추가 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                System.out.println("CLOSE ERROR");
            }
        }
    }
}
