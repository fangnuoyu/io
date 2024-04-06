package com.hrt.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@SpringBootTest
@Slf4j
class IoApplicationTests {

    @Test
    void contextLoads() throws IOException {
        /**
         * 拷贝文件
         */
//        // 1. 创建对象表示数据源
//        File origin = new File("E:\\data");
//        // 2. 创建对象表示目的地
//        File target = new File("E:\\dataMirror");
//        // 3. 调用方法开始拷贝
//        mirrorFile(origin, target);

        /**
         * 加解密文件
         */
//        File origin = new File("E:\\BaiduNetdiskDownload\\龙珠高清壁纸\\001.jpg");
//        File ency = new File("E:\\BaiduNetdiskDownload\\龙珠高清壁纸\\001ency.jpg");
//        File redu = new File("E:\\BaiduNetdiskDownload\\龙珠高清壁纸\\001redu.jpg");
//        // 加密处理
//        encyFile(origin, ency);
//        // 解密处理
//        reduFile(ency, redu);

        /**
         * 解压文件
         */
        File origin = new File("E:\\data.zip");
        File target = new File("E:\\ds\\");
        unZip(origin, target);

        /**
         * 压缩文件
         */
        toZip(origin, target);
    }

    /**
     * 拷贝文件
     * origin: 原文件(可以是一个文件或是一个文件夹)
     * target: 目标文件(可以是一个文件或是一个文件夹)
     * @param origin
     * @param target
     */
    void mirrorFile(File origin, File target) {
        // 创建目标文件夹
        target.mkdirs();
        // 获取原文件的目录
        File[] originFiles = origin.listFiles();
        // 遍历目录
        for (File originFile : originFiles) {
            if(originFile.isDirectory()) {
                mirrorFile(originFile, new File(target, originFile.getName()));
                continue;
            }
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(originFile);
                fos = new FileOutputStream(new File(target, originFile.getName()));
                byte[] bytes = new byte[4096];
                int len;
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        log.error("拷贝文件过程中, 出现输出流关闭异常");
                        throw new RuntimeException(e);
                    }
                }
                if(fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        log.error("拷贝文件过程中, 出现输入流关闭异常");
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    void encyFile(File origin, File target) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(origin);
            fos = new FileOutputStream(target);
            // 加密处理
            int b;
            while((b = fis.read()) != -1) {
                fos.write(b ^ 2);
            }
        }catch (Exception e) {

        }finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error("加密文件过程中, 出现输出流关闭异常");
                    throw new RuntimeException(e);
                }
            }
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("加密文件过程中, 出现输入流关闭异常");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    void reduFile(File origin, File target) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(origin);
            fos = new FileOutputStream(target);
            // 加密处理
            int b;
            while((b = fis.read()) != -1) {
                fos.write(b ^ 2);
            }
        }catch (Exception e) {

        }finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error("加密文件过程中, 出现输出流关闭异常");
                    throw new RuntimeException(e);
                }
            }
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("加密文件过程中, 出现输入流关闭异常");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 解压文件
     * @param origin
     * @param target
     */
    void unZip(File origin, File target) {
        ZipInputStream zipInputStream = null;
        try {
            // 创建解压流
            zipInputStream = new ZipInputStream(new FileInputStream(origin), Charset.forName("GBK"));
            // 获取解压流里面的每一个解压对象
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                System.out.println(zipEntry);
                if(zipEntry.isDirectory()) {
                    // 文件夹: 需要在目的地创建一个与压缩包解压对象的同名文件夹
                    File file = new File(target, zipEntry.toString());
                    file.mkdirs();
                }else {
                    // 文件: 需要读取压缩包中的文件,并把它存放到目的地文件夹中(按照层级目录存放)
                    FileOutputStream fos = null;
                    try {
                        // 文件: 需要读取压缩包中的文件,并把它存放到目的地文件夹中(按照层级目录存放)
                        fos = new FileOutputStream(new File(target, zipEntry.toString()));
                        byte[] zipByte = new byte[4096];
                        int len;
                        while ((len = zipInputStream.read(zipByte)) != -1) {
                            fos.write(zipByte, 0, len);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(fos != null) {
                            fos.close();
                            zipInputStream.closeEntry();
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    log.error("解压文件过程中, 出现输入流关闭异常");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 压缩文件
     * @param origin
     * @param target
     */
    void toZip(File origin, File target) throws IOException {
        origin = new File("E:\\ds\\data");
        target = new File(target, origin.getName() + ".zip");
        // 创建压缩流关联压缩包
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(target));
        toZip(origin, zos, origin.getName());
        // 释放资源
        zos.close();
    }

    /**
     * 作用: 获取origin的每一个文件, 变成ZipEntry对象,放入到压缩包中
     * 参数一: 数据源
     * 参数二: 压缩流
     * 参数三: 压缩包内部的路径
     * @param origin
     * @param zos
     * @param name
     */
    void toZip(File origin, ZipOutputStream zos, String name) throws IOException {
        File[] files = origin.listFiles();

        for (File file : files) {
            if(file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(name +  "\\" + file.getName());
                zos.putNextEntry(zipEntry);
                // 读取文件中的数据, 写到压缩包
                FileInputStream fis = new FileInputStream(file);
                int b;
                while((b = fis.read()) != -1) {
                    zos.write(b);
                }
                fis.close();
                zos.closeEntry();
            }else {
                // 判断文件夹,递归
                toZip(file, zos, name + "\\" + file.getName());
            }
        }
    }

}
