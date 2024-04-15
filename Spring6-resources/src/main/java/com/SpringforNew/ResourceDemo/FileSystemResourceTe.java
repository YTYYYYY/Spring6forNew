package com.SpringforNew.ResourceDemo;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;

public class FileSystemResourceTe {

    public static void main(String[] args) {
        loadFileSystemResourceTe("D:\\WorkSpace\\Spring6forNew\\Spring6forNew\\Spring6-resources\\src\\main\\resources\\Demo.txt");
    }

    public static void loadFileSystemResourceTe(String path){
        //创建 Resource 实现类的对象 UrlResource
        FileSystemResource resource = new FileSystemResource(path);
        System.out.println(resource.getFilename());
        System.out.println(resource.getDescription());
        //获取文件内容
        try {
            InputStream is = resource.getInputStream();
            byte[] bys = new byte[1024];
            int len;
            while ((len = is.read(bys)) != -1){
                System.out.println(new String(bys,0,len));
            }
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
