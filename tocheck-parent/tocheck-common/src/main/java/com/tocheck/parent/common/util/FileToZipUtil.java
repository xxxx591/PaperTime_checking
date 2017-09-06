package com.tocheck.parent.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 将文件夹下面的文件
 * 打包成zip压缩文件
 *
 * @author kchen
 */
public final class FileToZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileToZipUtil.class);

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     *
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFilePath    :压缩后存放路径
     * @param fileName       :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        if (sourceFile.exists() == false) {
            logger.warn("待压缩的文件目录：{}不存在.", sourceFilePath);
        } else {
            try {
                File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
                if (zipFile.exists()) {
                    logger.warn("{}目录下存在名字为:{}.zip" + "打包文件.", zipFilePath, fileName);
                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        logger.warn("待压缩的文件目录：{}里面不存在文件，无需压缩.", sourceFilePath);
                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //关闭流
                try {
                    if (null != bis) bis.close();
                    if (null != zos) zos.close();
                    if (null != fos) fos.close();
                    if (null != fis) fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    public static void multiFileToZip(List<File> files, String zipFilePath) {
        //生成的ZIP文件名为Demo.zip
        ZipOutputStream out = null;
        FileInputStream fis = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFilePath));
            //需要同时下载的两个文件result.txt ，source.txt
//            File[] file1 = {new File("e:/a.txt"),new File("e:/b.txt"),new File("e:/aa.txt"),new File("e:/bb.txt")};
            for (File file : files) {
                fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件
                byte[] buffer = new byte[1024];
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            logger.warn("文件打包失败",e);
        } finally {
            try {
                if (null != out) out.close();
                if (null != fis) fis.close();
            } catch (IOException e) {
                logger.error("文件打zip包时,关闭流失败..",e);
            }

        }
    }

//    public static void main(String[] args){
//        String sourceFilePath = "D:\\TestFile";
//        String zipFilePath = "D:\\tmp";
//        String fileName = "12700153file";
//        boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
//        if(flag){
//            System.out.println("文件打包成功!");
//        }else{
//            System.out.println("文件打包失败!");
//        }
//    }
}
