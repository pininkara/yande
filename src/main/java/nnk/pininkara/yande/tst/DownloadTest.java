// 2022/1/20 13:27

package nnk.pininkara.yande.tst;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import nnk.pininkara.yande.pojo.ImageData;
import nnk.pininkara.yande.utils.Utils;

import java.util.List;

public class DownloadTest {
    public static void main(String[] args) throws Exception {

       /* //设置代理服务器
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", "192.168.0.105");
        System.getProperties().put("proxyPort", "7890");*/

        String url="https://files.yande.re/jpeg/0b6ce47f511e5880cdeed3a28949e258/yande.re%20919445%20animal_ears%20ass%20cameltoe%20chintora0201%20genshin_impact%20japanese_clothes%20no_bra%20pantsu%20skirt_lift%20tail%20thong%20yae_miko_%28genshin_impact%29.jpg";
        //文件下载
        /*HttpUtil.downloadFile(url, FileUtil.file("g:/media/crawler"));
        System.out.println("完成~");*/
        Utils.downloadByUrl(url,"g:/media/crawler");

    }
}

