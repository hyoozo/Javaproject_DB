package exam_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BookInsertTest02 {
    public static void main(String[] args) {

//        addBook("자바의 정석 ","남궁석","2000",30000);
        addBook("쿼리를수정하다 ","이현주","2022",1000000);

    }

    //인서트 메소드만들기
    //PreparedStatement 로 사용해보기.
    private static void addBook(String title, String publisher, String year, int price) {
        Connection con = ConnectDatabase.makeConnection("javauser", "java1234");
        PreparedStatement pstmt = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO BOOK (BOOK_ID, TITLE, PUBLISHER, YEAR, PRICE) " +
                    "VALUES(BOOK_SEQ.NEXTVAL,?,?,?,?)");

            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, title);
            pstmt.setString(2, publisher);
            pstmt.setString(3, year);
            pstmt.setInt(4, price);

            int insertCount = pstmt.executeUpdate(); //실행해라.
            //입력이 되면 반환값 1. 그래서 int 로 받음.

            if (insertCount == 1) {
                System.out.println("레코드 추가 성공");
            } else {
                System.out.println("레코드 추가 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
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
