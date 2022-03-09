// 2022/2/10 9:57

package nnk.pininkara.yande.tst;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import nnk.pininkara.yande.utils.Utils;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDownloadTest {
    static long sum = 0;

    public static void main(String[] args) {
        FileReader reader = new FileReader("e:/eee.txt");
        String path = "g:/media/crawler/1/";
        List<String> list = reader.readLines();
        for (String s : list) {
            new DownThread(s, path).start();
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
