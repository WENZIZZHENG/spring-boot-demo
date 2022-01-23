# spring-boot-starter-data-redis缓存

## 1 概述

Spring Boot整合redis，这里封装了StringRedisTemplate常用方法



## 2 RedisTemplate序列化配置

```java
@EnableCaching
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisConfig {

    /**
     * 构造方法注入
     */
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * redisTemplate 序列化为json
     * 不需要redisTemplate，这个可不写。与spring cache没任何影响
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // key 序列化
        template.setKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(jackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 序列化
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        // value的序列化类型
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}
```

