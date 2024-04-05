package com.hrt.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        File origin = new File("E:\\BaiduNetdiskDownload\\龙珠高清壁纸\\001.jpg");
        File ency = new File("E:\\BaiduNetdiskDownload\\龙珠高清壁纸\\001ency.jpg");
        File redu = new File("E:\\BaiduNetdiskDownload\\龙珠高清壁纸\\001redu.jpg");
        // 加密处理
        encyFile(origin, ency);
        // 解密处理
        reduFile(ency, redu);
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

}
