package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataGenerator {

    int id;

    public Data generateData(int id, String thread){

            this.id = id;
            String status = new SimpleDateFormat("HH:mm:ss").format(new Date());
            String state = thread + "-" + status;

            Data model = new Data(id,state);

        return model;
    }
}
