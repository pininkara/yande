// 2022/1/20 14:16

package nnk.pininkara.yande.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import nnk.pininkara.yande.Default;
import nnk.pininkara.yande.pojo.Artists;
import nnk.pininkara.yande.pojo.ImageData;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * 获得响应体
     *
     * @param allTag tag
     * @param start  检索开始页码
     * @return 图片列表
     * @throws IOException 读取响应体的io异常
     */
    public static List<ImageData> getResponse(String allTag, int start, int end) throws IOException {
        List<ImageData> data = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            String url = "https://yande.re/post?page=" + i + "&tags=" + allTag;
            System.out.println("Url: " + url);
            String tempResponse = HttpRequest.get(url)
                    .timeout(30000)
                    .setHttpProxy(Default.proxyHost, Default.proxyPortInt)
                    .execute().body();
            List<ImageData> imageData = getData(tempResponse);
            if (!data.addAll(imageData)) {
                System.out.println("第" + i + "页没有图片~");
            }
        }
        return data;
    }

    /**
     * 从response中获取图片信息
     *
     * @param response 响应体
     * @return 图片信息
     * @throws IOException 读取响应体的io异常
     */
    private static List<ImageData> getData(String response) throws IOException {
        String line;
        List<String> json = new ArrayList<>();
        List<ImageData> data = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8))));

        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("Post.register")) {
                if (!line.contains("Post.register_tags")) {
                    String string = line.trim().substring(14);
                    json.add(string.substring(0, (string.length() - 1)));
                }
            }
        }
        for (String s : json) {
            data.add(JSONUtil.toBean(s, ImageData.class));
        }
        return data;
    }

    /**
     * 筛选满足质量因子的图片
     *
     * @param data   图片列表
     * @param factor 质量因子
     * @return 筛选后的图片列表
     */
    public static List<ImageData> chosenData(List<ImageData> data, int factor) {
        List<ImageData> chosen = new ArrayList<>();
        for (ImageData imageData : data) {
            if (imageData.getScore() >= factor) {
                chosen.add(imageData);
                System.out.println("Score: " + imageData.getScore()+" ID: "+imageData.getId());
            }
        }
        System.out.println("检索到" + data.size() + "张图片，筛选后还有" + chosen.size() + "张图片");
        return chosen;
    }

    /**
     * 无代理的图片列表下载
     *
     * @param chosenData 图片列表
     * @return true
     */
    public static boolean download(List<ImageData> chosenData) {
        for (ImageData data : chosenData) {
            String fileUrl = data.getFile_url();
            long size = HttpUtil.downloadFile(fileUrl, FileUtil.file("G:/test/yande"));
            System.out.println("Download size: " + size);
        }
        return true;
    }

    /**
     * 有代理的单张图片下载
     *
     * @param url  图片链接
     * @param path 保存路径
     * @return long型图片大小
     */
    public static synchronized long downloadByUrl(String url, String path) {
        //设置代理服务器
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", Default.proxyHost);
        System.getProperties().put("proxyPort", Default.proxyPortString);
        long size = HttpUtil.downloadFile(url, FileUtil.file(path));
        //System.out.println("已下载："+FileUtil.readableFileSize(size));
        return size;
    }

    /**
     * 获得Artist的页码数
     *
     * @param response html
     * @return 页码数
     */
    public static int getEndPage(String response) {
        int i = response.indexOf("next_page");
        String page = "";
        String page17 = String.valueOf(response.charAt(i - 17));
        String page16 = String.valueOf(response.charAt(i - 16));
        if (!page17.equals(">")) {
            page = page17 + page16;
        } else {
            page = page16;
        }
        return Integer.parseInt(page);
    }

    /**
     * 通过json获得Artists
     *
     * @param jsonPath json路径
     * @return Artists
     */
    public static Artists getArtistsByJson(String jsonPath) {
        FileReader reader = new FileReader(jsonPath);
        String json = reader.readString();
        return JSONUtil.toBean(json, Artists.class);
    }


}
