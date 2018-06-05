package com.cloud.disk.unit;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/5
 * Time: 16:59
 */
public class QrcodeUtil {

    public static byte[] generateQrcode(String content, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map map = new HashMap();
        map.put(EncodeHintType.MARGIN, 3);
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, map);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        byte[] bytes = imageToBytes(image, "jpeg");
        return bytes;
    }

    private static byte[] imageToBytes(BufferedImage image, String type) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, out);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return out.toByteArray();
    }

}
