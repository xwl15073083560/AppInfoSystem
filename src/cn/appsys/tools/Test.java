package cn.appsys.tools;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @author 小未来leo
 * @date 2019/8/21 14:37
 * @params
 * @return
 */
public class Test {
    public static void main(String[] args) {
        Random r=new Random();
        if (r.nextBoolean()) System.out.println("瘾哥欠我10块");
        else System.out.println("瘾哥欠我20块");

    }
}
