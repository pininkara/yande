// 2022/1/21 13:37

package nnk.pininkara.yande.pojo;

public class Artist {
    private String name;
    private long id;

    public Artist(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
