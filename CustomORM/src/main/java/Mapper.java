import Anootations.MyEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Mapper {

    public static Class getClassWithEntityAnnotation(Object obj) {

        if (obj.getClass().isAnnotationPresent(MyEntity.class)) {
            return obj.getClass();
        } else {
            System.out.println("This type has not @Entity Annotation");
            return null;
        }

    }

    public static String getPropertyType(Field field) {
        String type = "";
        if (field.getType().equals(int.class)) {
            type = "int4";
        } else if (field.getType().equals(double.class)) {
            type = "float8";
        } else if (field.getType().equals(String.class)) {
            type = "varchar(255)";
        } else if (field.getType().equals(Date.class)) {
            type = "date";
        }
        return type;
    }

}
