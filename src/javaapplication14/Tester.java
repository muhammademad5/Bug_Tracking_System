package javaapplication14;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;
public class Tester extends GlobalFunctions {
    Tester()
    {
        
    }
    public void define(String n,String t,String pri,int lvl,String project_name,String date,String status,String testerdef)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            PreparedStatement adding = cn.prepareStatement("insert into bugs values(?,?,?,?,?,?,?,?,?,?,?)");
            // name type priority level projectname status is assigned
            Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
            adding.setString(1,n);
            adding.setString(2,t);
            adding.setString(3, pri);
            adding.setInt(4,lvl);
            adding.setString(5,project_name);
            adding.setDate(6, sqlDate);
            adding.setString(7,status);
            adding.setInt(8, 0);
            adding.setString(9,null);
            adding.setString(10, testerdef);
            adding.setString(11,null);
            adding.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, Tester > Define fun");
            e.printStackTrace();
        } catch (ParseException p)
        {
            p.getErrorOffset();
        }
    }
    public void assign(String bugname,String assigneddev)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            ///////////////////////////////////////////////// ASSIGNING /////////////////////////////////
            PreparedStatement assign = cn.prepareStatement("update bugs set isAssigned = 1,AssignedTo = ? where Name = ?");
            assign.setString(1, assigneddev);
            assign.setString(2, bugname);
            assign.executeUpdate();
            ///////////////////////////////////////////////// EMAIL AUTOMATICALLY /////////////////////////
            String stmnt = "select * from Users where UserName = ?";
            PreparedStatement getemail = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            getemail.setString(1, assigneddev);
            ResultSet rs = getemail.executeQuery();
            int x;
            if (!rs.next())
            {
                x = 0;
            }
            else
            {
                rs.absolute(1);
                x = rs.getInt(4) + 1;
                System.out.println(rs.getInt(4));
            }
            PreparedStatement recemail = cn.prepareStatement("update Users set ReceivedEmails = ? where UserName = ?");
            recemail.setInt(1, x);
            recemail.setString(2,assigneddev);
            recemail.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, Tester > assign fun");
            e.printStackTrace();
        }
    }
    public void deleteall()
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            PreparedStatement assign = cn.prepareStatement("delete from bugs");
            assign.executeUpdate();
            System.out.println("Database Cleared");             
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, Tester > delete all fun");
            e.printStackTrace();
        }
    }
    public void assignscreen(String bugname,String src)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            PreparedStatement s = cn.prepareStatement("update bugs set ImageSrc = ? where Name = ?");
            s.setString(1, src);
            s.setString(2, bugname);
            s.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
