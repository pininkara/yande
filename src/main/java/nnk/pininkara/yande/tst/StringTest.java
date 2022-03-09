// 2022/2/10 11:21

package nnk.pininkara.yande.tst;

import cn.hutool.core.io.file.FileReader;

public class StringTest {
    public static void main(String[] args) {
        FileReader reader = new FileReader("g:/download/Edge/222.html");
        String string = reader.readString();
        int i = string.indexOf("next_page");
        String page="";
        String page17 = String.valueOf(string.charAt(i - 17));
        String page16 = String.valueOf(string.charAt(i - 16));
        if (!page17.equals(">")) {
            page= page17 + page16;
        }else{
            page= page16;
        }
        System.out.println(page);
    }
}
