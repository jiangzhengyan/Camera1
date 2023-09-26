package com.example.myapplication;



import java.util.ArrayList;

/**
 * 本类的主要功能是 :  布局适配工具类
 *
 * @author jiang_zheng_yan  2021/2/4 14:10
 */

public class LayoutUtil {
    private ArrayList<Object> fileListCopy;
    //android  Configuration
    //public static Configuration config=AppApplication.getInstance().getResources().getConfiguration();

    /**
     * 是否为手机设备
     *
     * @return true/false
     */
    public static boolean isPhone() {
        int smallestScreenWidthDp = AppApplication.getInstance().getResources().getConfiguration().smallestScreenWidthDp;
        return smallestScreenWidthDp < 600;
    }

    /**
     * 是否为pad设备
     *
     * @return true/false
     */
    public static boolean isPad() {
        return !isPhone();
    }

    /**
     * 获取横竖屏,是否是竖屏
     *
     * @return true/false
     */
    public static boolean isScreenPortrait() {

        return AppApplication.getInstance().getResources().getConfiguration().orientation == 1;
    }

    /**
     * 获取横竖屏,是否是横屏
     *
     * @return true/false
     */
    public static boolean isScreenlandScape() {
        return !isScreenPortrait();
    }



}
