package com.example.lsj.mvpdemo.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.lsj.mvpdemo.BuildConfig;


/**
 * @Description: 自定义log
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 10:07
 */
public class LP {

    //是否开启debug,默认根据运行方式选择
    private static boolean isBug = BuildConfig.DEBUG;
    //分隔符
    private static String separator = ",";
    /**
     * 是否显示log附加信息,包括线程名+方法名+行数
     */
    private static boolean showInfo = true;

    /**
     * 构造方法私有化，防止多次实例化。
     */
    private LP() {
    }

    /**
     * 公共日志打印方法
     *
     * @param tag
     * @param msg
     */
    public static void logI(String tag, String msg) {
        if (isBug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        if (isBug && !TextUtils.isEmpty(msg)) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(stackTraceElement);
            Log.w("lsj--" + tag, "<<<<<<<------------------------");
            Log.w("lsj--" + tag, msg);
            Log.w("lsj--" + tag, getLogInfo(stackTraceElement));
        }
    }

    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     * 则TAG为MainActivity
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return tag;
    }

    /**
     * 输出日志所包含的信息
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        if (!showInfo) {
            return "";
        }
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取生日输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("  ==>[ ");
        logInfoStringBuilder.append(" threadID=" + threadID).append(separator);
        logInfoStringBuilder.append(" threadName=" + threadName).append(separator);
        logInfoStringBuilder.append(" fileName=" + fileName).append(separator);
        logInfoStringBuilder.append(" className=" + className).append(separator);
        logInfoStringBuilder.append(" methodName=" + methodName).append(separator);
        logInfoStringBuilder.append(" lineNumber=" + lineNumber);
        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }
}
