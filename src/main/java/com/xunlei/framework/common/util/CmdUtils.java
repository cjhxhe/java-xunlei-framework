package com.xunlei.framework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 系统命令执行工具类
 */
public class CmdUtils {

    private static Logger LOG = LoggerFactory.getLogger(CmdUtils.class);

    /**
     * 执行指定命令
     *
     * @param ip
     * @return
     */
    public static String tracert(String ip) {
        SystemEnvironment env = CmdUtils.getSystemEnvironment();
        return env.tracert(ip);
    }

    /**
     * 调用python3执行python脚本
     *
     * @param fullPathNamePyFile脚本完整路径
     * @param params脚本要显示传递的参数
     */
    public static String python3(String fullPathNamePyFile, String... params) {
        SystemEnvironment env = CmdUtils.getSystemEnvironment();
        return env.python3(fullPathNamePyFile, params);
    }

    /**
     * 获取当前系统环境
     *
     * @return
     */
    private static SystemEnvironment getSystemEnvironment() {
        String os = System.getProperty("os.name");
        if (os == null) {
            return null;
        }
        if (os.toLowerCase().startsWith("windows")) {
            return new WindowsEnvironment();
        }
        return new LinuxEnvironment();
    }

    interface SystemEnvironment {

        /**
         * 跟踪路由
         *
         * @param ip 任务名称(映像名称)
         * @return
         */
        String tracert(String ip);

        /**
         * 调用python3执行python脚本
         *
         * @param fullPathNamePyFile脚本完整路径
         * @param params脚本要显示传递的参数
         */
        String python3(String fullPathNamePyFile, String[] params);

        /**
         * 执行命令
         *
         * @param cmdarray
         * @return
         */
        default String exec(String[] cmdarray) {
            Process proc = null;
            InputStream is = null;
            BufferedReader reader = null;
            try {
                proc = Runtime.getRuntime().exec(cmdarray);
                is = proc.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "GBK"));
                String line;
                StringBuffer sbf = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    LOG.info(line);
                    sbf.append(line).append("\n");
                }
                proc.waitFor();
                return sbf.toString();
            } catch (Exception e) {
                LOG.error("执行路由跟踪出错，原因：", e);
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    if (proc != null) {
                        proc.destroy();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    static class WindowsEnvironment implements SystemEnvironment {

        @Override
        public String tracert(String ip) {
            String[] cmdarray = {"cmd", "/C", "tracert -h 15 " + ip};
            return exec(cmdarray);
        }

        @Override
        public String python3(String fullPathNamePyFile, String[] params) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("python");
            buffer.append(" \"").append(fullPathNamePyFile).append("\"");
            if (params != null && params.length > 0) {
                for (String param : params) {
                    buffer.append(" \"").append(param).append("\"");
                }
            }
            return exec(new String[]{"cmd", "/C", buffer.toString()});
        }
    }

    static class LinuxEnvironment implements SystemEnvironment {

        @Override
        public String tracert(String ip) {
            String[] cmdarray = {"/bin/sh", "-c", "traceroute -m 15 " + ip};
            return exec(cmdarray);
        }

        @Override
        public String python3(String fullPathNamePyFile, String[] params) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("python3");
            buffer.append(" \"").append(fullPathNamePyFile).append("\"");
            if (params != null && params.length > 0) {
                for (String param : params) {
                    buffer.append(" \"").append(param).append("\"");
                }
            }
            return exec(new String[]{"/bin/sh", "-c", buffer.toString()});
        }
    }

    public static void main(String[] args) {
        String result = CmdUtils.python3("D:/MyWork2/workspace-live/LmDataPython/redash_toolbelt/refresh_dashboard.py", "http://192.168.96.205:5000",
                "RrDa38sw6lyHRMMWEQvvdt60J6WaMdIRNNzvdphD", " -_3", "getPublicUrl");
        System.out.println(result);
    }
}
