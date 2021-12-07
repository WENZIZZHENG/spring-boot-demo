package com.example.service;

import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import com.example.BaseTest;
import com.example.entity.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * <p>
 * 多数据源测试
 * </p>
 *
 * @author MrWen
 **/
public class DynamicTest extends BaseTest {

    /**
     * 推荐---自定义publicKey加密，配置文件需要配置 spring.datasource.dynamic.public-key
     * https://www.kancloud.cn/tracy5546/dynamic-datasource/2280963
     */
    @Test
    public void customEncrypt() throws Exception {
        //每次操作都是随机生成的
        String[] arr = CryptoUtils.genKeyPair(512);
        System.out.println("privateKey:  " + arr[0]);
        System.out.println("publicKey:  " + arr[1]);
        System.out.println("url:  " + CryptoUtils.encrypt(arr[0], "jdbc:mysql://127.0.0.1:3306/test"));
        System.out.println("username:  " + CryptoUtils.encrypt(arr[0], "root"));
        System.out.println("password:  " + CryptoUtils.encrypt(arr[0], "root"));
    }


    @Autowired
    private IAdminService adminService;

    private final Random random = new Random();

    /**
     * 新增（默认在主库）
     */
    @Test
    public void addAdmin() {
        for (int i = 0; i < 10; i++) {
            Admin admin = new Admin();
            admin.setUserName("测试用户" + random.nextInt());
            admin.setAvatar("avatar" + random.nextInt(100));
            adminService.save(admin);
        }
    }

    /**
     * 查默认主库的
     */
    @Test
    public void selectAdminFromDs() {
        adminService.list().forEach(System.out::println);
    }


    /**
     * 查从库servlet的，默认轮询
     */
    @Test
    public void selectAdminFromDsGroup() {
        for (int i = 0; i < 6; i++) {
            adminService.selectSlaveAdmin().forEach(System.out::println);
            System.out.println("============================================");
            System.out.println("============================================");
        }
    }
}
