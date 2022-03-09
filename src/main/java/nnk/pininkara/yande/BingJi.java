// 2022/1/21 13:28

package nnk.pininkara.yande;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONUtil;
import nnk.pininkara.yande.utils.Utils;
import nnk.pininkara.yande.pojo.Artist;
import nnk.pininkara.yande.pojo.Artists;
import nnk.pininkara.yande.pojo.ImageData;

import java.io.IOException;
import java.util.List;

//为json中的每一个artist得到一个文件列表
public class BingJi {
    public static void main(String[] args) throws IOException {
        FileWriter jsonWriter=new FileWriter(Default.jsonPath);
        Artists artists=Utils.getArtistsByJson(Default.jsonPath);
        for (Artist artist : artists.getArtists()) {
            List<ImageData> response = Utils.getResponse(artist.getName(), 1,3);
            FileWriter writer = new FileWriter("g:/media/st/addition/" + artist.getName() + ".txt");
            int flag = 0;
            long maxId=artist.getId();
            for (ImageData imageData : response) {
                if (imageData.getId() > artist.getId()) {
                    writer.append(imageData.getFile_url() + "\n");
                    flag++;
                    if (imageData.getId()>maxId){
                        maxId=imageData.getId();
                    }
                }
            }
            System.out.println(artist.getName() + "筛选后有" + flag + "张图");
            System.out.println("当前最大ID为："+maxId);
            artist.setId(maxId);
        }
        jsonWriter.write(JSONUtil.toJsonStr(artists));
        System.out.println("Json写入完毕，Over~");

    }
}
