// 2022/1/21 18:49

package nnk.pininkara.yande.utils;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONUtil;
import nnk.pininkara.yande.pojo.Artist;
import nnk.pininkara.yande.pojo.Artists;

//一个快速更新json的方法
public class UpdateJson {
    public static void main(String[] args) {
        FileReader reader=new FileReader("g:/media/st/json.txt");
        FileWriter writer=new FileWriter("g:/media/st/json.txt");
        Artists artists = JSONUtil.toBean(reader.readString(), Artists.class);

        /*list.add(new Artist("amashiro_natsuki", 854930));
        list.add(new Artist("chintora0201", 886286));
        list.add(new Artist("ke-ta", 790756));
        list.add(new Artist("mafuyu_(chibi21)", 848896));
        list.add(new Artist("mignon", 879859));
        list.add(new Artist("quan_(kurisu_tina)", 836273));
        list.add(new Artist("rurudo", 817555));
        list.add(new Artist("taku_michi", 837201));
        list.add(new Artist("tamano_kedama", 842305));
        list.add(new Artist("wsman", 888901));*/

        artists.getArtists().add(new Artist("tsubasa_tsubasa",2));

        writer.write(JSONUtil.toJsonStr(artists));


    }
}
