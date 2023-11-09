package com.xunlei.framework.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机数
 *
 * @author lxr
 */
public class RandomUtils extends RandomStringUtils {

    /**
     * 获取一个随机Long整数，最小值0，最大值为max-1
     */
    public static Long getLong(Long max) {
        return getLong(0L, max);
    }

    /**
     * 获取一个随机Long整数，最小值min，最大值为max-1
     */
    public static Long getLong(Long min, Long max) {
        long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
        return rangeLong;
    }

    // 获取一个随机整数，最大值为max
    public static int getInt(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public static int getInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;

    }

    public static List<Integer> getInt(int min, int max, int count) {
        List<Integer> result = new ArrayList<Integer>();
        if (max - min < count)
            return null;
        while (count > 0) {
            int r = getInt(min, max);
            boolean flag = false;
            for (Integer i : result) {
                if (r == i.intValue()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                result.add(r);
                count--;
            }
        }
        return result;
    }


    /**
     * 获取指定长度的随机字母+数字组合，区分大小写
     *
     * @param length
     * @return
     */
    public static String getRandomCharAndNum(Integer length) {
        StringBuffer str = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomNum = random.nextInt(62);
            // 暂定 0-9 随机数字，10-35 大写字母，36-61 小写字母
            if (0 <= randomNum && randomNum <= 9) {
                str.append(randomNum);
            } else if (10 <= randomNum && randomNum <= 35) {
                // 以大写字母ascii码开头
                str.append((char) (65 + randomNum - 10));
            } else {
                // 以小写字母ascii码开头
                str.append((char) (97 + randomNum - 36));
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 100000; i++) {
            System.out.println(getRandomCharAndNum(10));
        }
    }
}
