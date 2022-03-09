// 2022/2/10 9:34

package nnk.pininkara.yande;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONUtil;
import nnk.pininkara.yande.pojo.Artist;
import nnk.pininkara.yande.pojo.Artists;
import nnk.pininkara.yande.pojo.ImageData;
import nnk.pininkara.yande.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

//BingJi的升级版，不再将下载链接写入文件，而是多线程下载
public class BingJiDownload {
    static long sum;  //总计下载大小
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader(Default.jsonPath);
        FileWriter jsonWriter=new FileWriter(Default.jsonPath);
        String json = reader.readString();

        Artists artists = JSONUtil.toBean(json, Artists.class);

        for (Artist artist : artists.getArtists()) {
            //没有质量因子筛选，照单全收
            List<ImageData> response = Utils.getResponse(artist.getName(), 1,8);
            int flag = 0;
            long maxId=artist.getId();
            for (ImageData imageData : response) {
                //检查id是否大于现有id
                if (imageData.getId() > artist.getId()) {
                    new DownThread(imageData.getFile_url(),Default.targetPath+artist.getName()+"/").start();
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


    static class DownThread extends Thread {
        private ReentrantLock lock = new ReentrantLock();
        String path;
        String url;

        public DownThread(String url, String path) {
            this.path = path;
            this.url = url;
        }

        @Override
        public void run() {
            long size = Utils.downloadByUrl(url, path);
            lock.lock();
            sum += size;
            System.out.println("总计下载：" + FileUtil.readableFileSize(sum));
            lock.unlock();
        }
    }
}
