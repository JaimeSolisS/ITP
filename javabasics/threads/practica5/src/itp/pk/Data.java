package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    public int id;
    public String state;


    public Data(int id, String threadName) {
        this.id = id;
        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        String state = threadName + "-" + status;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", status='" + state + '\'' +
                '}';
    }
}
