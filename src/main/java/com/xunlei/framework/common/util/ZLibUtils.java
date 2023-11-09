/*
 * @(#) ZLibUtils.java 1.0.0 2017年3月30日 下午8:34:15
 */
package com.xunlei.framework.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * ZLib压缩工具类
 */
public abstract class ZLibUtils {

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data) throws IOException {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
            }
            compresser.end();
        }
        return output;
    }

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @param os   输出流
     */
    public static void compress(byte[] data, OutputStream os) throws IOException {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);
        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (java.util.zip.DataFormatException e) {
            output = data;
            // incorrect header check
            // 该文本没有压缩或不是用的zlib压缩的
            // 不需要解压
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            decompresser.end();
        }
        return output;
    }

    /**
     * 解压缩
     */
    public static byte[] decompress(InputStream is) throws IOException {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        int len = 1024;
        try {
            byte[] buf = new byte[len];
            while ((len = iis.read(buf, 0, len)) > 0) {
                o.write(buf, 0, len);
            }
        } finally {
            try {
                if (o != null) {
                    o.close();
                }
            } catch (IOException e) {
            }
            try {
                if (iis != null) {
                    iis.close();
                }
            } catch (IOException e) {
            }
        }
        return o.toByteArray();
    }

}
