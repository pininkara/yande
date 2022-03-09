// 2022/2/10 12:12

package nnk.pininkara.yande.utils;

import nnk.pininkara.yande.Default;
import nnk.pininkara.yande.pojo.Artist;
import nnk.pininkara.yande.pojo.Artists;

import java.io.File;

//为每个Artist建立文件夹
public class mkDir {
    public static void main(String[] args) {
        Artists artists=Utils.getArtistsByJson(Default.jsonPath);
        for (Artist artist : artists.getArtists()) {
            String dirPath=Default.targetPath+artist.getName()+"/";
            File file=new File(dirPath);
            if (!file.exists()){
                file.mkdir();
            }
        }
    }
}
