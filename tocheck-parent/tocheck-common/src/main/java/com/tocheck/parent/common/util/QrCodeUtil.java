package com.tocheck.parent.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author pangliang
 * @create 2017-04-08 14:54
 **/
public class QrCodeUtil {

    public static String createQrCode(String url, String path, String fileName) throws IOException, WriterException {
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "png";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
        //生成二维码
        String filePath = fileName + ".png";
        File outputFile = new File(path + filePath);
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        return filePath;
    }
}
