package com.xunlei.framework.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 图片处理工具类
 */
public class ImageUtils {

    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * @param imgUrl 网络图片地址
     * @param type   图片类型
     * @return String  转换结果
     * @throws
     * @Description 网络图片转换成二进制字符串
     */
    public static String getImgHexString(String imgUrl, String type) {
        String res = null;
        try {
            URL url = new URL(imgUrl); // 创建URL
            URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码
            urlconn.connect();
            HttpURLConnection httpconn = (HttpURLConnection) urlconn;
            // 服务器返回的状态
            int ret = httpconn.getResponseCode();
            if (ret != HttpURLConnection.HTTP_OK) // 不等于HTTP_OK则连接不成功
                logger.error("获取图片失败，errcode：" + ret);
            else {
                BufferedInputStream bis = new BufferedInputStream(urlconn.getInputStream());

                BufferedImage bm = ImageIO.read(bis);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bm, type, bos);
                bos.flush();
                byte[] data = bos.toByteArray();
                res = ByteUtils.byteToHexStr(data);
                bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 通过url 取到2进制流
     *
     * @param imgUrl
     * @return
     */
    public static byte[] getImgBytes(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(10000);
            BufferedImage image = ImageIO.read(connection.getInputStream());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", out);
            return out.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 高效图片压缩成jpg
     * 若图片横比w=200小，高比h=300小，不变
     * 若图片横比w=200小，高比h=300大，高缩小到300，图片比例不变
     * 若图片横比w=200大，高比h=300小，横缩小到200，图片比例不变
     * 若图片横比w=200大，高比h=300大，图片按比例缩小，横为200或高为300
     * <p>
     * quality表示压缩质量比例 选填0-1,填1表示不降低质量
     * <p>
     * 比BufferedImage类压缩效果好很多，而且图片小
     *
     * @param srcImageFile
     * @param w
     * @param h
     * @return
     * @throws Throwable
     */
    public static byte[] compress(InputStream srcImageFile, int w, int h, float quality) throws Exception {
        return ImageUtils.compress(srcImageFile, w, h, quality, "jpg");
    }

    /**
     * 高效图片压缩成jpg
     * 若图片横比w=200小，高比h=300小，不变
     * 若图片横比w=200小，高比h=300大，高缩小到300，图片比例不变
     * 若图片横比w=200大，高比h=300小，横缩小到200，图片比例不变
     * 若图片横比w=200大，高比h=300大，图片按比例缩小，横为200或高为300
     * <p>
     * quality表示压缩质量比例 选填0-1,填1表示不降低质量
     * <p>
     * 比BufferedImage类压缩效果好很多，而且图片小
     *
     * @param srcImageFile
     * @param w
     * @param h
     * @param format       文件格式：jpg|png
     * @return
     * @throws Exception
     */
    public static byte[] compress(InputStream srcImageFile, int w, int h, float quality, String format) throws Exception {
        BufferedImage image = ImageIO.read(srcImageFile);
        int width = image.getWidth();
        int height = image.getHeight();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (width < w && height < h) {
            Thumbnails.of(image)
                    .scale(1f)
                    .outputQuality(quality)//如果要降低质量可选设置,0.7f表示70%的质量
                    .outputFormat(format)
                    .toOutputStream(out);
        } else {
            Thumbnails.of(image)
                    .size(w, h)
                    .outputQuality(quality)//如果要降低质量可选设置,0.7f表示70%的质量
                    .outputFormat(format)
                    .toOutputStream(out);
        }
        return out.toByteArray();
    }

    /**
     * base64转成图片
     *
     * @param base64Str
     * @return
     */
    public static BufferedImage base64StrToImage(String base64Str) {
        if (StringUtils.isEmpty(base64Str)) {
            return null;
        }
        if (base64Str.contains(",")) {
            base64Str = base64Str.substring(base64Str.indexOf(",") + 1);
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(base64Str);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片设置圆角
     *
     * @param srcImage
     * @param padding
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage, int padding) throws IOException {
        return ImageUtils.setRadius(srcImage, -1, 0, -1);
    }

    /**
     * 图片设置圆角
     *
     * @param srcImage
     * @param radius
     * @param border
     * @param padding
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage, int radius, int border, int padding) throws IOException {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        if (radius == -1) {
            radius = width / 10;
        }
        if (padding == -1) {
            padding = width / 20;
        }
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;

        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setColor(Color.WHITE);
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.drawImage(setClip(srcImage, radius), padding, padding, null);
        if (border != 0) {
            gs.setColor(Color.WHITE);
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, radius, radius);
        }
        gs.dispose();
        return image;
    }

    /**
     * 图片设置圆角
     *
     * @param srcImage
     * @return
     * @throws IOException
     */
    private static BufferedImage setRadius(BufferedImage srcImage) throws IOException {
        int radius = (srcImage.getWidth() + srcImage.getHeight()) / 6;
        return setRadius(srcImage, radius, 2, 5);
    }

    /**
     * 图片切圆角
     *
     * @param srcImage
     * @param radius
     * @return
     */
    private static BufferedImage setClip(BufferedImage srcImage, int radius) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        gs.drawImage(srcImage, 0, 0, null);
        gs.dispose();
        return image;
    }
}
