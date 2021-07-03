package com.lon.lin.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TT {
    public static void main(String[] args) throws IOException {
        String url = "http://localhost/ylsb-zheng.jpg";
        URL url1 = new URL(url);
        URLConnection uc = url1.openConnection();
        InputStream inputStream = uc.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
//        B

//        FileOutputStream out = new FileOutputStream("D:\\a.jpg");
//        out.write(bytes,0, i - 1 );
    }
}
