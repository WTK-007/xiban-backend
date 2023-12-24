package com.wtk.xiban.once;

import com.alibaba.excel.EasyExcel;

import java.util.List;

/**
 * 导入 Excel
 *
 * @author wtk
 */
public class ImportExcel {

    public static void main(String[] args) {
        // 写法1：JDK8+
        // since: 3.0.0-beta1
        String fileName ="D:\\Desktop\\计算机就业\\Java\\项目\\用户中心项目\\user-center-backend\\src\\main\\resources\\testExcel.xlsx";
//        readByListener(fileName);
        synchronousRead(fileName);
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置

    }

    /**
     * 监听器读取
     * @param fileName
     */
    public static void readByListener(String fileName){
        EasyExcel.read(fileName, XingQiuTableUserInfo.class, new TableListener()).sheet().doRead();
    }

    /**
     * 同步读取
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<XingQiuTableUserInfo> totalDataList = EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
        for (XingQiuTableUserInfo xingQiuTableUserInfo : totalDataList) {
            System.out.println(xingQiuTableUserInfo);
        }
    }

}
