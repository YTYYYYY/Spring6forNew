package com.SpringforNew.make.bean;

import com.SpringforNew.make.anno.Bean;
import com.SpringforNew.make.anno.Di;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnoApContext implements ApContext{

    //创建map集合，存放bean对象
    private Map<Class,Object> beanFac = new HashMap<>();
    private static String rootPath;

    //返回对象
    @Override
    public Object getBean(Class clazz) {return beanFac.get(clazz);}

    //设置包扫描的规则
    public AnnoApContext(String scanPackages) {
        try {
            //1.把.替换成\
            String packagePaths = scanPackages.replaceAll("\\.", "\\\\");
            //2.获取包的绝对路径
            Enumeration<URL> urls = Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResources(packagePaths);
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                rootPath = filePath.substring(0,filePath.length()-packagePaths.length());
                //包扫描
                loadBean(new File(filePath));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        loadDi();
    }

    //包扫描过程
    private void loadBean(File file) throws Exception {
        //1.判断是否是文件夹
        if(file.isDirectory()){
            //2.获取文件夹内所有内容
            File[] childFiles = file.listFiles();
            //3.判断文件夹内是否为空，直接返回
            if(childFiles == null || childFiles.length == 0) return;
            //4.如果文件夹不为空，获取文件内所有数据
            for(File child : childFiles){
                //4.1.遍历得到的每个File对象，如果还是文件夹，递归
                if(child.isDirectory()) loadBean(child);
                else{
                    //4.2.File对象是文件
                    //4.3.得到包路径+类名称部分-字符串截取
                    String pathWithClass = child.getAbsolutePath()
                                        .substring(rootPath.length() - 1);
                    //4.4.判断当前文件类型是否为.class
                    if(pathWithClass.contains(".class")){
                        //4.5.如果是.class类型，把路径\换成.   把.class去掉
                        String fullName = pathWithClass.replaceAll("\\\\", "\\.")
                                                    .replace(".class", "");
                        //4.6.判断类上面是否有注解@Bean，如果有实例化
                        Class<?> cla = Class.forName(fullName);
                        if (! cla.isInterface()){
                            Bean annotation = cla.getAnnotation(Bean.class);
                            if(annotation != null){
                                //4.7.实例化之后放进map集合中
                                Object instance = cla.getConstructor().newInstance();
                                //4.7.1 判断当前类如果有接口，放接口class作为map的key
                                if(cla.getInterfaces().length > 0){
                                    beanFac.put(cla.getInterfaces()[0],instance);
                                }else {
                                    beanFac.put(cla,instance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadDi(){
        //实例化对象在 beanFac 的map集合里面
        //1.遍历beanFac的map集合
        Set<Map.Entry<Class, Object>> entries = beanFac.entrySet();
        for(Map.Entry<Class, Object> entry : entries){
            //2.获取map集合的每个对象(value),每个对象属性获取到
            Object obj = entry.getValue();
            Class<?> cla = obj.getClass();
            Field[] dFields = cla.getDeclaredFields();
            //3.遍历每个对象属性数组，得到每个属性
            for(Field f : dFields){
                //4.判断每个属性上面是否有@Di注解
                Di annotation = f.getAnnotation(Di.class);
                if(annotation != null){
                    f.setAccessible(true);
                    //5.如果有@Di注解，把对象进行设置(注入)
                    try {
                        f.set(obj,beanFac.get(f.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
