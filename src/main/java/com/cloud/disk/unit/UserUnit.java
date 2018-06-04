package com.cloud.disk.unit;

import com.cloud.disk.domain.User;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 21:12
 */
public class UserUnit {

    /**
     * 生成随机字符串的字符集
     */
    private static char[] code = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'o', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'O', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * 生成字符串的长度
     */
    private static int number = 6;

    public static String getSalt() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            int index = random.nextInt(code.length);
            builder.append(code[index]);
        }
        return builder.toString();
    }

    public static String passwordEncryption(String password, String salt) {
        SimpleHash simpleHash = new SimpleHash("MD5", password, salt, 3);
        return simpleHash.toHex();
    }

    public static boolean verifyUser(User user, Long userId) {
        if (user != null && !String.valueOf(userId).equals("")) {
            if (user.getUserId() == userId)
                return true;
        }
        return false;
    }
}
