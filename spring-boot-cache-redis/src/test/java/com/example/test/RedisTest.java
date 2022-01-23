package com.example.test;

import com.example.BaseTest;
import com.example.dto.UserDto;
import com.example.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * redis测试
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class RedisTest extends BaseTest {

    @Autowired
    private IRedisService redisService;

    @Test
    public void baseTest() throws InterruptedException {
        redisService.set("key1", "value1", 10L);
        System.out.println("redisService.get(\"key1\") = " + redisService.get("key1"));

        Thread.sleep(12000);
        System.out.println("redisService.get(\"key1\") = " + redisService.get("key1"));
    }

    @Test
    public void testGetObject() throws InterruptedException {
        //key存在则直接放回，不存在就触发查询方法
        for (int i = 0; i < 3; i++) {
            UserDto userDto = redisService.getObject("user", UserDto.class,
                    () -> {
                        return this.getUserDto();
                    },
                    10L
            );
            System.out.println("userDto = " + userDto);
        }

        //模拟过期
        Thread.sleep(12000);

        UserDto userDto = redisService.getObject("user", UserDto.class,
                () -> {
                    return this.getUserDto();
                },
                10L
        );
        System.out.println("userDto = " + userDto);
    }

    private UserDto getUserDto() {
        log.info("触发getUserDto方法啦~");
        UserDto dto = new UserDto();
        Random random = new Random();
        dto.setUserName("UserName" + random.nextInt(10));
        dto.setAge(random.nextInt(10));
        return dto;
    }


    @Test
    public void testGetList() throws InterruptedException {
        //key存在则直接放回，不存在就触发查询方法
        for (int i = 0; i < 3; i++) {
            List<UserDto> dtoList = redisService.getList("user", UserDto.class,
                    () -> {
                        return this.getUserDtoList();
                    },
                    10L
            );
            System.out.println("dtoList = " + dtoList);
        }

        //模拟过期
        Thread.sleep(12000);

        List<UserDto> dtoList = redisService.getList("user", UserDto.class,
                () -> {
                    return this.getUserDtoList();
                },
                10L
        );
        System.out.println("dtoList = " + dtoList);
    }


    private List<UserDto> getUserDtoList() {
        log.info("触发getUserDtoList方法啦~");
        List<UserDto> dtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserDto dto = new UserDto();
            Random random = new Random();
            dto.setUserName("UserName" + random.nextInt(10));
            dto.setAge(random.nextInt(10));
            dtoList.add(dto);
        }
        return dtoList;
    }

}
