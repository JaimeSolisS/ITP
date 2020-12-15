package itp.pk.Controller;
import itp.pk.Model.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataGenerator {
    public static List<Data> generateData(int amount){
        List<Data> list = new ArrayList<>();;

        for (Long i=1L; i<=amount; i++){
            Long id = i;
            String name = "John";
            String key = id + name.substring(0,3);
            String status = new SimpleDateFormat("HH:mm:ss").format(new Date());

            Data worker = new Data(id, name, key, status);
            list.add(worker);
        }

        return list;
    }
}
