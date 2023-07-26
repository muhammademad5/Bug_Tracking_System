package javaapplication14;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class Admin extends GlobalFunctions {
    
    Admin()
    {
        
    }
    public void adduser(String username,int pw,String userrole)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            //PreparedStatement s = cn.prepareStatement("insert into table1 values(")
            PreparedStatement adding = cn.prepareStatement("insert into Users values(?,?,?,?)");
            adding.setString(1,username);
            adding.setString(2, userrole);
            adding.setInt(3,pw);
            adding.setInt(4,0);
            adding.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Couldnt Open DataBase Admin -> AddUser");
            e.printStackTrace();
        }
    }
    public void updateuser(String username,String update)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            PreparedStatement updating = cn.prepareStatement("update Users set UserRole = ? where UserName = ?");
            updating.setString(1,update);
            updating.setString(2,username);
            updating.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase Admin -> UpdateUser");
            e.printStackTrace();
        }
    }
    public void deleteuser(String username)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            PreparedStatement deleting = cn.prepareStatement("delete from Users where UserName = ?");
            PreparedStatement resetassign = cn.prepareStatement("update bugs set AssignedTo = ? where AssignedTo = ?");
            resetassign.setString(1,null);
            resetassign.setString(2,username);
            deleting.setString(1,username);
            deleting.executeUpdate();
            resetassign.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase Admin -> deleteuser");
            e.printStackTrace();
        }
    }
    public void deleteall()
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            PreparedStatement assign = cn.prepareStatement("delete from Users");
            assign.executeUpdate();
            System.out.println("Database Cleared");             
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, Admin -> delete all fun");
            e.printStackTrace();
        }
    }
    public ResultSet showuser()
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            String stmnt = "select * from users";
            PreparedStatement showall = cn.prepareStatement(stmnt,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = showall.executeQuery();
            return rs;
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, Admin -> Show all Users");
            e.printStackTrace();
        }
        return null;
    }
}
