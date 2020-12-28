package itp.pk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    public String state;

    public Data(String threadName) {
        String status = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
        String state = threadName + "-" + status;
        this.state = state;
    }

    @Override
    public String toString() {
        return "state='" + state + '\'';
    }
}
