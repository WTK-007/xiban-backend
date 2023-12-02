package com.wtk.xiban.once;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导入星球用户到数据库
 */
public class ImportXingQiuUser {
    public static void main(String[] args) {

        String fileName = "D:\\Desktop\\计算机就业\\Java\\项目\\用户中心项目\\user-center-backend\\src\\main\\resources\\testExcel.xlsx";

        List<XingQiuTableUserInfo> userInfoList =
                EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
        System.out.println("总数 = " + userInfoList.size());
        Map<String, List<XingQiuTableUserInfo>> listMap =
                userInfoList.stream()
                .filter(userInfo -> StringUtils.isNotEmpty(userInfo.getUsername()))
                .collect(Collectors.groupingBy(XingQiuTableUserInfo::getUsername));
        System.out.println("不重复昵称数 = " + listMap.keySet().size());

    }
}
