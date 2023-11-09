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
        if (max - min < count) return null;
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
}
