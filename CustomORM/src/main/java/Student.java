import Anootations.MyEntity;
import Anootations.MyId;
import lombok.Data;

import java.util.Date;
@Data
@MyEntity(tableName = "tests")
public class Student {
    @MyId
    private   int id;
    private String name;
    private   String surname;
    private int age;
    private Date birthday;
}
