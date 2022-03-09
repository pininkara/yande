// 2022/1/20 13:27

package nnk.pininkara.yande;

import cn.hutool.core.io.FileUtil;
import nnk.pininkara.yande.pojo.ImageData;
import nnk.pininkara.yande.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

//通过TAG获取下载链接，并写入文件
public class JiaoJi {
    static long sum = 0;

    public static void main(String[] args) throws IOException {
        Set<String> set = new HashSet<>();
        String allTags = "";

        System.out.println("输入0表示不下载，只写入文件，其他则为下载到保存目录");
        Scanner input = new Scanner(System.in);
        String str = input.next();
        String path = "g:/media/crawler/1/"; //下载保存目录

        /*set.add("-genshin_impact");
        set.add("-loli");
        set.add("-pantyhose");
        set.add("-blue_archive");*/
        set.add("azur_lane");

        for (String s : set) {
            allTags = allTags + "+" + s;
        }
        System.out.println("AllTags :"+allTags);
        //获取图片数据
        List<ImageData> response = Utils.getResponse(allTags, 1, 764);
        //筛选
        List<ImageData> chosenData = Utils.chosenData(response, 200);
        //下载或将文件链接写入文件
        if (!str.equals("0")) {
            System.out.println("正在下载~");
            for (ImageData imageData : response) {
                new DownThread(imageData.getFile_url(), path).start();
            }
        } else {
            String str2="";
            while (!str2.equals("0")){
                System.out.println("输入0表示写入文件，其他则为再次筛选");
                Scanner input2 = new Scanner(System.in);
                str2 = input2.next();
                if (!str2.equals("0")){
                    chosenData=Utils.chosenData(response,Integer.parseInt(str2));
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("e:/"+allTags.substring(1,allTags.length())+".txt"));
            for (ImageData imageData : chosenData) {
                bufferedWriter.write(imageData.getFile_url());
                bufferedWriter.newLine();
            }
            System.out.println("文件写入完毕~");
            bufferedWriter.close();
        }

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

