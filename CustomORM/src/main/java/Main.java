import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, IllegalAccessException {
//        Student t=new Student();
//
//        Manager.createTable(t);
        Student s=new Student();
        s.setName("Mubariz");
        s.setSurname("Mammadzada");
        s.setAge(21);
        s.setBirthday(Date.valueOf("2000-01-16"));

//        Manager.insertData(s);
        Manager.deleteData(s);
    }
}
