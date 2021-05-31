import Anootations.MyEntity;
import Anootations.MyId;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.Annotation;
import java.util.Properties;

public class Manager {

    public static void createTable(Object o) throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("customorm.conf")));

        String url = properties.getProperty("app.url") + properties.getProperty("app.dbName") + "?"
                + "user=" + properties.getProperty("app.username") + "&password=" + properties.getProperty("app.password");
        Connection conn = DriverManager.getConnection(url);

        Class c = Mapper.getClassWithEntityAnnotation(o);
        String tableName = "";

        if (c != null) {
            tableName = c.getName() + "s";

        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= c.getDeclaredFields().length - 1; i++) {
            if (c.getDeclaredFields()[i].isAnnotationPresent(MyId.class)) {
                builder.append(c.getDeclaredFields()[i].getName() + " " +
                        Mapper.getPropertyType(c.getDeclaredFields()[i]) + " " + "primary key" + ",");
            } else {
                if (i == c.getDeclaredFields().length - 1) {
                    builder.append(c.getDeclaredFields()[i].getName() + " " +
                            Mapper.getPropertyType(c.getDeclaredFields()[i]) + " " + "not null");
                } else {
                    builder.append(c.getDeclaredFields()[i].getName() + " " +
                            Mapper.getPropertyType(c.getDeclaredFields()[i]) + " " + "not null" + ",");
                }
            }


        }
        String s = builder.toString();
        System.out.println(s);
        System.out.println("Create table" + " " + tableName + "("
                + builder.toString() + ")");
        Statement statement = conn.createStatement();
        statement.execute("Create table" + " " + tableName + "("
                + builder.toString() + ")");

    }

    public static <T> void insertData(T o) throws IOException, SQLException, IllegalAccessException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("customorm.conf")));

        String url = properties.getProperty("app.url") + properties.getProperty("app.dbName") + "?"
                + "user=" + properties.getProperty("app.username") + "&password=" + properties.getProperty("app.password");
        Connection conn = DriverManager.getConnection(url);
        ;
        String tableName = "";
        if (Mapper.getClassWithEntityAnnotation(o) != null) {
            tableName = Mapper.getClassWithEntityAnnotation(o).getName() + "s";
        }
        StringBuilder builder = new StringBuilder();
        int counterr = 0;
        for (Field f : o.getClass().getDeclaredFields()) {
            counterr++;
            f.setAccessible(true);
            System.out.println(f.get(o));
            if (f.isAnnotationPresent(MyId.class)) {
                builder.append((int)( Math.random()*1000 )+ ",");
            } else {
                if (counterr == o.getClass().getDeclaredFields().length) {

                    builder.append("'" + f.get(o) + "'");
                } else {

                    builder.append("'" + f.get(o) + "'" + ",");
                }
            }

        }
        System.out.println("INSERT  into" + " " + tableName + " " + "values" + " ("
                + builder.toString() + ")");
        Statement statement = conn.createStatement();
        statement.execute("INSERT  into" + " " + tableName + " " + "values" + " ("
                + builder.toString() + ")");

    }

    public static <T> void deleteData(T obj) throws IOException, SQLException, IllegalAccessException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Path.of("customorm.conf")));

        String url = properties.getProperty("app.url") + properties.getProperty("app.dbName") + "?"
                + "user=" + properties.getProperty("app.username") + "&password=" + properties.getProperty("app.password");
        Connection conn = DriverManager.getConnection(url);
        ;
        String tableName = "";
        if (Mapper.getClassWithEntityAnnotation(obj) != null) {
            tableName = Mapper.getClassWithEntityAnnotation(obj).getName() + "s";
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }

        try {
            PreparedStatement ps = null;
            try {
                ps = conn
                        .prepareStatement("DELETE FROM " +
                                tableName +
                                " WHERE " + fields[0].getName() + " = " + fields[0].get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

