package com.SpringforNew.ResourceDemo;

import org.springframework.core.io.UrlResource;

public class UrlResourceTe {

    public static void main(String[] args) {
        //loadUrlResource("http://www.baidu.com");
        loadUrlResource("file:Demo.txt");
    }

    //访问前缀http、ftp、file
    public static void loadUrlResource(String path){
        try {
            //创建 Resource 实现类的对象 UrlResource
            UrlResource urlResource = new UrlResource(path);
            //获取资源信息
            System.out.println(urlResource.getURL());
            System.out.println(urlResource.getFilename());
            System.out.println(urlResource.getDescription());
            System.out.println(urlResource.getInputStream().read());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
