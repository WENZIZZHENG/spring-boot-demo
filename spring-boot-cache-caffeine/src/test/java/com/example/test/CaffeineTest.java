package com.example.test;

import com.example.BaseTest;
import com.example.dto.ItemStock;
import com.example.dto.UserDto;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * <p>
 * caffeine测试
 * </p>
 *
 * @author MrWen
 **/
public class CaffeineTest extends BaseTest {

    @Autowired
    private Cache<Long, UserDto> userDtoCache;

    @Autowired
    private Cache<Long, ItemStock> stockCache;


    @Test
    public void test1() {
        //1） 新增
        // 添加或者更新一个缓存元素
        userDtoCache.put(1L, UserDto.builder()
                .id(1L)
                .userName("迪丽热巴")
                .age(18)
                .build());

        // 查找缓存，如果缓存不存在则生成缓存元素,  如果无法生成则返回null
        UserDto user2 = userDtoCache.get(2L, key -> {
            // 根据key去数据库查询数据
            return UserDto.builder()
                    .id(key)
                    .userName("柳岩")
                    .age(24)
                    .build();
        });


        //2) 查询
        // 取数据，上面的get也算
        UserDto user1 = userDtoCache.getIfPresent(1L);

        System.out.println("user1 = " + user1);
        System.out.println("user2 = " + user2);

        //3) 删除
        // 移除一个缓存元素
        userDtoCache.invalidate(1L);
        user1 = userDtoCache.getIfPresent(1L);
        System.out.println("user1 = " + user1);

        // 批量失效key
        userDtoCache.invalidateAll(Arrays.asList(1L, 2L));
        // 失效所有的key
        userDtoCache.invalidateAll();

        System.out.println("defaultGF2 = " + userDtoCache.getIfPresent("defaultGF"));
    }


}
