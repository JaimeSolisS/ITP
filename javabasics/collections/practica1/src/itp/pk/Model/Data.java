package itp.pk.Model;

public class Data {

    public Long id;
    public String name;
    public String key;
    public String status;


    public Data(Long id, String name, String key, String status) {
        this.id = id;
        this.name=name;
        this.key=key;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
