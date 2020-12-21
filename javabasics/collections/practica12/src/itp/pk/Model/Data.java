package itp.pk.Model;

public class Data implements Comparable<Data> {
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

    public int compareTo(Data e){ //implementing abstract method.
        if(id>e.id){
            return 1;
        }
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setStatus(String status){
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