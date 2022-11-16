package exam_jdbc;

import java.sql.*;
import java.util.Scanner;

public class BooksPreparedStatementTest {
    public static void showMeun(){
        System.out.println("선택하세요.");
        System.out.println("1. 데이터 입력");
        System.out.println("2. 데이터 삭제");
        System.out.println("3. 데이터 검색");
        System.out.println("4. 프로그램 종료");
        System.out.print("선택 : ");

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String title, publisher, year;
        int choice, price;

        while (true) {
            showMeun();
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("제목 :");
                    title = sc.nextLine();
                    System.out.print("저자 :");
                    publisher = sc.nextLine();
                    System.out.print("발행일 :");
                    year = sc.nextLine();
                    System.out.print("가격 :");
                    price = sc.nextInt();
                    addBook(title, publisher, year, price);
                    break;
                case 2:
                    System.out.print("책번호 :");
                    int book_id = sc.nextInt();
                    deleteBook(book_id);
                    break;
                case 3:
                    readAllBook();
                    break; //반복 벗어남
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    sc.close(); //밑에서 return 하니깐 스캐너 먼저 종료.
                    return; //메서드(메인) 종료

            }
        }
    }

    public static void addBook(String title, String publisher, String year, int price) {
         Connection con = ConnectDatabase.makeConnection("javauser", "java1234");
        //디폴트로 자동 커밋이지만 커밋을 주고 싶을때
        //con.setAutoCommit(false);

         PreparedStatement pstmt = null;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO BOOK(BOOK_ID, TITLE, PUBLISHER, YEAR, PRICE) VALUES(BOOK_SEQ.NEXTVAL,?,?,?,?)");
            pstmt = con.prepareStatement(sb.toString());

            pstmt.setString(1, title);
            pstmt.setString(2, publisher);
            pstmt.setString(3, year);
            pstmt.setInt(4, price);


            int result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println("레코드 입력 완료");
                //con.commit();
            } else {
                System.out.println("레코드 입력 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("close error");

            }
        }
    }
    private static void readAllBook(){
        Connection con = ConnectDatabase.makeConnection("javauser", "java1234");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int book_id, price;
        String title, publisher, year;


        try {
            //stmt = con.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT * FROM BOOK ");

            pstmt = con.prepareStatement(sb.toString());
            rs = pstmt.executeQuery(sb.toString());

            System.out.println("출력");
            while (rs.next()) {
                book_id = rs.getInt("book_id");
                title = rs.getString("title");
                publisher = rs.getString("publisher");
                year = rs.getString("year");
                price = rs.getInt("price");
                System.out.printf("%-7d |\t %-11s |\t %-6s |\t %s |\t %d\n", book_id, title, publisher, year, price);
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR");
            System.exit(0);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("close error");

            }
        }
    }

    private static void deleteBook(int book_id) {
        Connection con = ConnectDatabase.makeConnection("javauser", "java1234");
        //디폴트로 자동 커밋이지만 커밋을 주고 싶을때
        //con.setAutoCommit(false);

        PreparedStatement pstmt = null;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("DELETE FROM BOOK WHERE BOOK_ID = ?");
            pstmt = con.prepareStatement(sb.toString());

            pstmt.setInt(1, book_id);


            int result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println("레코드 삭제 완료");
                //con.commit();
            } else {
                System.out.println("레코드 삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("close error");
            }
        }

    }
}
