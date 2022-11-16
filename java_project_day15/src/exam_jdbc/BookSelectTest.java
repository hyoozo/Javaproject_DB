package exam_jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BookSelectTest {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        int book_id, price;
        String title, publisher, year;
//        Scanner sc = new Scanner(System.in);
        try{
            con = ConnectDatabase.makeConnection("javauser", "java1234"); //데이터베이스 연결
            stmt = con.createStatement();

//            System.out.print("검색할 제목 입력 :");
//            String title = sc.nextLine();

            StringBuffer sql = new StringBuffer();                // * 요기 마지막에 공백 꼭 줘야함.
            sql.append("SELECT book_id, title, publisher, year, price ");
            sql.append("FROM book ");
//            sql.append("WHERE title = ' " + title + " ' ");
                                    //' '  작은 따옴표로 묶어 주어야 한다.  WHERE title = 'java'; 와 같은 구문 (문자여서)

            rs = stmt.executeQuery(sql.toString());
//            rs = stmt.executeQuery("select book_id, title, publisher, year, price from book where title = '"+title+"'");
            System.out.println("출력");
            while (rs.next()) {
                book_id = rs.getInt("book_id");
                title = rs.getString("title");
                publisher = rs.getString("publisher");
                year = rs.getString("year");
                price = rs.getInt("price");
                System.out.printf("%-7d |\t %-11s |\t %-6s |\t %s |\t %d\n", book_id, title, publisher, year, price);
            }
            // 마지막으로 메인실행전에 디벨로퍼에서 자료가 모두 입력된 후 커밋해주어야 한다.

        } catch (SQLException e){
            System.out.println("[쿼리문 ERROR] \n"+e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
