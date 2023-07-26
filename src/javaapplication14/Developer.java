package javaapplication14;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class Developer extends GlobalFunctions {
    Developer()
    {
        
    }
    
    public void changestatus(String newstatus,String bugname)
    {
        try {
            Connection cn = DriverManager.getConnection(url,user,password);
            ///////////////////////////////////////////////////// CHANGE STATUS ///////////////////////////////////////////////
            PreparedStatement changing = cn.prepareStatement("update bugs set status = ? where Name = ?");
            changing.setString(1,newstatus);
            changing.setString(2,bugname);
            changing.executeUpdate();
            ////////////////////////////////////////////////////// EMAILS INCREMENT AUTOMATICALLY /////////////////////////////
            /////// GETTING Definer Name ///////
            PreparedStatement SelectDefineUser = cn.prepareStatement("select * from bugs where Name = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            SelectDefineUser.setString(1, bugname);
            ResultSet SelectDefineUserSet = SelectDefineUser.executeQuery();
            SelectDefineUserSet.absolute(1);
            String TesterName = SelectDefineUserSet.getString(10);
            ///// Getting Initial RecievedEmails Number /////
            PreparedStatement SelectTesterRow = cn.prepareStatement("Select * from Users where UserName = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            SelectTesterRow.setString(1, TesterName);
            ResultSet TesterRow = SelectTesterRow.executeQuery();
            TesterRow.absolute(1);
            int TesterInitialRecEmails = TesterRow.getInt(4);
            //// Incrementing ////
            PreparedStatement updateRec = cn.prepareStatement("update Users set ReceivedEmails = ? where UserName = ?");
            updateRec.setInt(1, TesterInitialRecEmails + 1);
            updateRec.setString(2, TesterName);
            updateRec.executeUpdate();
            ////
            /*PreparedStatement selecttester = cn.prepareStatement("select * from Users where UserRole = 'Tester'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet testers = selecttester.executeQuery();
            String stmnt = "update Users set ReceivedEmails = ? where UserRole = 'Tester'";
            PreparedStatement recemails = cn.prepareStatement(stmnt);
            int i = 1;
            while(testers.next())
            {
                testers.absolute(i);
                recemails.setInt(1, (testers.getInt(4) + 1));
            }
            recemails.executeUpdate();*/
        } catch (SQLException e)
        {
            System.out.println("Couldn't Open DataBase Developer -> ChangeStatus fun");
            e.printStackTrace();
        }
    }
}
