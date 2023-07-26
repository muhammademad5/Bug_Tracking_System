package javaapplication14;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;

public class GlobalFunctions {
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Bugs";
    String user = "admin";
    String password = "admin";
    
    GlobalFunctions()
    {
        
    }
    public String checkuser(String username,int pw)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String statement = "select * from Users where UserName = ? AND pw = ?";
            PreparedStatement s = cn.prepareStatement(statement,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s.setString(1, username);
            s.setInt(2,pw);
            ResultSet rs = s.executeQuery();
            if ( !rs.next())
            {
                return null;
            }
            else
            {
                rs.absolute(1);
                return (rs.getString(2).toLowerCase().trim());
            }
        } catch ( SQLException e)
        {
            System.out.println("Error GlobalFunctions -> checkuser");
            e.printStackTrace();
        }
        return null;
    }
    public int checkuserByNameOnly(String username)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String statement = "select * from Users where UserName = ?";
            PreparedStatement s = cn.prepareStatement(statement,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            if ( !rs.next())
            {
                return 0;
            }
            else
            {
                return 1;
            }
        } catch ( SQLException e)
        {
            System.out.println("Error GlobalFunctions -> checkuser");
            e.printStackTrace();
        }
        return 0;
    }
    public int checkBugByNameOnly(String bugname)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String statement = "select * from bugs where Name = ?";
            PreparedStatement s = cn.prepareStatement(statement,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s.setString(1, bugname);
            ResultSet rs = s.executeQuery();
            if ( !rs.next())
            {
                return 0;
            }
            else
            {
                return 1;
            }
        } catch ( SQLException e)
        {
            System.out.println("Error GlobalFunctions -> checkuser");
            e.printStackTrace();
        }
        return 0;
    }
    public int checkuserbynameandrole(String username,String userrole)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String statement = "select * from Users where UserName = ? and UserRole = ?";
            PreparedStatement s = cn.prepareStatement(statement,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s.setString(1, username);
            s.setString(2, userrole);
            ResultSet rs = s.executeQuery();
            if ( !rs.next())
            {
                return 0;
            }
            else
            {
                return 1;
            }
        } catch ( SQLException e)
        {
            System.out.println("Error GlobalFunctions -> checkuser by name only");
            e.printStackTrace();
        }
        return 0;
    }
    public ResultSet displayall()
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String stmnt = "select * from bugs";
            PreparedStatement s = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery();
            return rs;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public int checkemail(String username)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String stmnt = "select * from Users where UserName = ?";
            PreparedStatement s = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            PreparedStatement decrement_email = cn.prepareStatement("update Users set ReceivedEmails = 0 where UserName = ?");
            decrement_email.setString(1,username);
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            if (!rs.next())
            {
                // user not found
                return 2;
            }
            else
            {
                rs.absolute(1);
                if (rs.getInt(4) == 0)
                {
                    return 0;
                }
                else
                {
                    decrement_email.executeUpdate();
                    return 1;
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 2;
    }
    public ResultSet displayassigned(String username)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String stmnt = "select * from bugs where IsAssigned = 1 and AssignedTo = ?";
            PreparedStatement s = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s.setString(1, username);
            ResultSet rs = s.executeQuery();
            return rs;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet displayallusers()
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String stmnt = "select * from Users";
            PreparedStatement s = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery();
            return rs;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public String returnsrc(String bugname)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String stmnt = "select * from bugs where Name = ?";
            PreparedStatement s = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            s.setString(1,bugname);
            ResultSet rs = s.executeQuery();
            rs.absolute(1);
            return rs.getString(11);
        } catch (SQLException e )
        {
            e.printStackTrace();
        }
        return "";
    }
}
