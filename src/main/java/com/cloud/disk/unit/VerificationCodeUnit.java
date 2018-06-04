package com.cloud.disk.unit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/20
 * Time: 16:32
 * 图片验证码专用吧
 */
public class VerificationCodeUnit {

    /**
     * 验证码代码库
     */
    private final static char[] CODE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'o', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'O', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * 验证码数量
     */
    private final static int CODE_NUMBER = 4;

    private static int width = 90;// 定义图片的width

    private static int height = 30;// 定义图片的height

    private static int fontHeight = 18;//字体高度

    private static int lineNumber = 12;//干扰线数量

    private static int offsetX = 10; //代码偏移量

    private static int offsetY = 21;//代码偏移量

    /**
     * 获取随机验证码
     *
     * @return 返回随机验证码
     */
    public static String randomCode() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_NUMBER; i++) {
            builder.append(CODE[random.nextInt(CODE.length)]).append(" ");
        }
        return builder.toString();
    }

    /**
     * 返回验证码和对应验证码的图片
     *
     * @return
     */
    public static Map<String, Object> createImage() throws IOException {
        char[] code = randomCode().toCharArray();//获取随机验证码
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//创建内存图片
        Graphics graphics = bufferedImage.getGraphics();//获取画布
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);//根据指定颜色和指定坐标画出指定面积的颜色范围
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);//设置字体样式
        graphics.setFont(font);//为画布设置字体
        graphics.setColor(Color.CYAN);
        graphics.drawRect(0, 0, width - 1, height - 1);//画边框线
        graphics.drawRoundRect(0, 0, width, height, 10, 10);
        graphics.setColor(Color.LIGHT_GRAY);
        Random random = new Random();
        for (int i = 0; i < lineNumber; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y2 = random.nextInt(12);
            graphics.drawLine(x, y, x + x1, y + y2);
        }//画干扰线

        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < code.length; i++) {
            if (String.valueOf(code[i]).equals(" ")) {
                continue;
            }
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            graphics.setColor(new Color(red, green, blue));
            graphics.drawString(String.valueOf(code[i]), (i + 1) * offsetX, offsetY);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "JPEG", outputStream);
        byte[] bytes = outputStream.toByteArray();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("login_code", String.valueOf(code).replaceAll(" ", ""));
        map.put("codeImage", bytes);
        System.out.println(String.valueOf(code).replaceAll(" ", ""));
        return map;
    }
}
