package com.example.first_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@SpringBootApplication
public class FirstDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstDemoApplication.class, args);

    }

    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    String index(){
        try {
            //ffffffff
            StringBuilder urlInfo = getURLInfo("https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432", "utf-8");
            save("D://study//test.txt",urlInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello Spring Boot!";
    }

    public static StringBuilder getURLInfo(String urlInfo,String charset) throws Exception {
        //读取目的网页URL地址，获取网页源码
        URL url = new URL(urlInfo);
        HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
        InputStream is = httpUrl.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            //这里是对链接进行处理
            line = line.replaceAll("</?a[^>]*>", "");
            //这里是对样式进行处理
            line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");
            sb.append(line);
        }
        is.close();
        br.close();
        return sb;
    }

    public  void save(String filePath,StringBuilder stringBuilder) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bw=new BufferedWriter(fileWriter);
            bw.write(stringBuilder.toString().trim());
            bw.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
