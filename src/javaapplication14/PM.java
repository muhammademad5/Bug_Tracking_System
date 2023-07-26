package javaapplication14;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class PM extends GlobalFunctions {
    PM()
    {
        
    }
    public int assignedbugnum(String username)
    {
        try {
            int bugscounter = 0;
            Connection cn = DriverManager.getConnection(url,user,password);
            String selectstmnt_all = "select * from bugs where AssignedTo = ?";
            PreparedStatement s = cn.prepareStatement(selectstmnt_all,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s.setString(1,username);
            ResultSet bugs_count = s.executeQuery();
            while(bugs_count.next())
            {
                bugscounter++;
            }
            return bugscounter;
        } catch (SQLException e)
        {
            System.out.println("Couldn't Connect , PM -> assignedbugnum");
            e.printStackTrace();
        }
        return 0;
    }
    public int checksolvednum(String username)
    {
        try
        {   
            int solvedcounter = 0;
            Connection cn = DriverManager.getConnection(url,user,password);
            String selectstmnt_solved = "select * from bugs where AssignedTo = ? and Status = 'Closed'";
            PreparedStatement s2 = cn.prepareStatement(selectstmnt_solved,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s2.setString(1,username);
            ResultSet solved_count = s2.executeQuery();
            while(solved_count.next())
            {
                solvedcounter++;
            }
            return solvedcounter;
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, PM -> Check solved num");
            e.printStackTrace();
        }
        return 0;
    }
    public int definednum(String username)
    {
        try
        {   
            int defcounter = 0;
            Connection cn = DriverManager.getConnection(url,user,password);
            String selectstmnt_solved = "select * from bugs where DefinedBy = ?";
            PreparedStatement s2 = cn.prepareStatement(selectstmnt_solved,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            s2.setString(1,username);
            ResultSet def_count = s2.executeQuery();
            while(def_count.next())
            {
                defcounter++;
            }
            return defcounter;
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase, PM -> Check solved num");
            e.printStackTrace();
        }
        return 0;
    }
}
