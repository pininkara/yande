// 2022/1/21 13:38

package nnk.pininkara.yande.pojo;

import java.util.List;

public class Artists {
    private List<Artist> artists;

    public Artists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return "Artists{" +
                "artists=" + artists +
                '}';
    }
}
