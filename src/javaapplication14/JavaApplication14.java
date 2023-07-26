package javaapplication14;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class JavaApplication14 {

    public static void main(String[] args) {
        Interface frame = new Interface();
        frame.setVisible(true);
        Tester tester = new Tester();
        Admin admin = new Admin();
        Developer developer = new Developer();
        GlobalFunctions g = new GlobalFunctions();
    }
    
}
