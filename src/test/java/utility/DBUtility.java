package utility;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBUtility extends PropertyFilesLoader {



//    public static  void insertUpdateDeleteFromDB(String query) throws IOException, SQLException {
//        Connection con=DriverManager.getConnection(GetProperty("connectionString"),GetProperty("dbUserName"),GetProperty("dbPassWord"));
//       Statement stmt=con.createStatement();
//       stmt.execute(query);
//        GenericMethods.writeLogInfo(query+"  <<<<<<<query executed>>>>>>>");
//       con.close();
//    }
//
//
//    public static  void retriveDataFromDB(String query) throws IOException, SQLException {
//        Connection con=DriverManager.getConnection(GetProperty("connectionString"),GetProperty("dbUserName"),GetProperty("dbPassWord"));
//        Statement stmt=con.createStatement();
//        ResultSet rs=stmt.executeQuery(query);
//        while (rs.next())
//        {
//            int sid=  rs.getInt("IDSTUDENT");
//            String scol1=  rs.getString("STUDENTCOL");
//            String scol2=  rs.getString("STUDENTCOL1");
//            System.out.println(sid+" "+scol1+" "+scol2);
//        }
//
//        GenericMethods.writeLogInfo(query+"  <<<<<<<query executed>>>>>>>");
//        con.close();
//    }
//
//
//
//
//    public static void main(String[] args) throws SQLException, IOException {
////        insertUpdateDeleteFromDB("INSERT  INTO STUDENT VALUES(2544,'chandra','chirra')"); //INSERT
//       // insertUpdateDeleteFromDB("UPDATE STUDENT SET STUDENTCOL='SHEKHAR' WHERE IDSTUDENT=500"); //UPDATE
//       // insertUpdateDeleteFromDB("SELECT * FROM STUDENT");
//        //insertUpdateDeleteFromDB("DELETE FROM STUDENT WHERE IDSTUDENT =45");//DELETE
//        retriveDataFromDB("select idstudent,studentcol,studentcol1 from student");
//
//    }
//


    static Connection con = null;
    static Statement statement = null;
    static ResultSet rs = null;
    static ResultSetMetaData rsmd = null;
    static PreparedStatement preparedstatement = null;
    static SimpleDateFormat smpldateformat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
    // GenericMethods gm = new GenericMethods();

    public static Connection connectDb() {
        System.out.println("Connecting to database" + "\n" + smpldateformat.format(new Date()) + "\n............");
        try {
            con = DriverManager.getConnection(GetProperty("connectionString"), GetProperty("dbUserName"), GetProperty("dbPassWord"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static Statement getStatement() {
        try {
            statement = connectDb().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static void Db_insert_update_delete(String SQl_Query) {
        try {
            GenericMethods.writeLogInfo("Updating the  records");
            PreparedStatement preparedStatement = connectDb().prepareStatement(SQl_Query);
            String QueryString =SQl_Query.substring(0,6);
            int rows = preparedStatement.executeUpdate(SQl_Query);
            if (SQl_Query.substring(0, 6).equalsIgnoreCase("update")) {
                System.err.println(rows + "  : Updated SuccessFully");
            } else if (SQl_Query.substring(0, 6).equalsIgnoreCase("insert")) {
                System.err.println(rows + "  : Inserted SuccessFully");
            } else if (SQl_Query.substring(0, 6).equalsIgnoreCase("delete")) {
                System.err.println(rows + "  : deleted SuccessFully");
            }
            con.close();
        } catch (
                Exception e) {
            System.out.

                    println(e);
        }

    }


    public static String showDatabase(String SQl_Query) throws SQLException {
        try {
            GenericMethods.writeLogInfo("Showing records");
            rs = getStatement().executeQuery(SQl_Query);
            rsmd = rs.getMetaData();
            int columnNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnNumber; i++) {
                    System.out.println(rs.getString(i) + "\t");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        connectDb().close();
        return rs.getString(1);
    }

    public static ArrayList<String> showDatabaseAsList(String SQl_Query) throws SQLException {
        ArrayList<String> resultList = new ArrayList<String>();
        try {
            GenericMethods.writeLogInfo("Showing records");
            rs = getStatement().executeQuery(SQl_Query);
            rsmd = rs.getMetaData();
            int columnNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnNumber; i++) {
                    resultList.add(rs.getString(i));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return resultList;

    }


    public static void main(String[] args) throws SQLException {
//        showDatabaseAsList("SELECT lastname FROM EMPLOYEES");
        Db_insert_update_delete("delete from STUDENT WHERE IDSTUDENT=245");
        Db_insert_update_delete("INSERT  INTO STUDENT VALUES(4146,'chandra','chirra')");
        Db_insert_update_delete("UPDATE STUDENT SET STUDENTCOL='Pasha' WHERE IDSTUDENT=4146");
    }


}