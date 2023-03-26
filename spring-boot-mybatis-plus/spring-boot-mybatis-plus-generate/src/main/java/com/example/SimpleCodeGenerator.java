package com.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.example.entity.base.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 简单的代码生成器
 * </p>
 *
 * @author MrWen
 **/
public class SimpleCodeGenerator {

    /**
     * 包父路径
     */
    private static final String PACKAGE_PARENT_PATH = "com.example";
    /**
     * 作者
     */
    private static final String AUTHOR = "MrWen";
    /**
     * 需要生成的表
     */
    private static final List<String> TABLE_LIST = Arrays.asList("t_admin", "t_user");
    /**
     * 设置过滤表前缀
     */
    private static final String TABLE_PREFIX = "t_";
    /**
     * 项目当前路径,单体项目直接这样就可以。String projectPath = System.getProperty("user.dir");
     * 因为这里是在父项目下，需要往后移一下路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir") + "/spring-boot-mybatis-plus/spring-boot-mybatis-plus-generate";
    /**
     * 数据库url
     */
    private static final String URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8";
    /**
     * 数据库username
     */
    private static final String USER_NAME = "root";
    /**
     * 数据库password
     */
    private static final String PASSWORD = "root";
    /**
     * 设置mapperXml路径
     */
    private static final String MAPPER_XML_PATH = "/src/main/resources/mapper";
    /**
     * entity父类
     */
    private static final Class<?> SUPER_ENTITY_CLASS = BaseEntity.class;
    /**
     * 父类字段(数据库字段列名)
     */
    private static final String[] SUPER_ENTITY_COLUMNS = {"create_time", "create_name", "update_time", "update_name", "version", "deleted", "tenant_id"};

    public static void main(String[] args) {
        FastAutoGenerator fastAutoGenerator = FastAutoGenerator
                //配置数据源
                .create(URL, USER_NAME, PASSWORD)
                //全局配置
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()//禁止打开输出目录
                            .dateType(DateType.TIME_PACK)//时间格式，只使用 java.util.date 代替  默认LocalDate
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(PROJECT_PATH + "/src/main/java"); // 指定输出目录
                })

                //包配置
                .packageConfig(builder -> {
                    builder.parent(PACKAGE_PARENT_PATH) // 设置父包名
//                            .moduleName("robot") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, PROJECT_PATH + MAPPER_XML_PATH)); // 设置mapperXml生成路径
                })

                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_LIST) // 设置需要生成的表名
                            .addTablePrefix(TABLE_PREFIX) // 设置过滤表前缀

                            //controller配置
                            .controllerBuilder()
                            //基础Controller
                            //.superClass(BaseController.class)
                            .enableHyphenStyle()
                            .enableRestStyle()
                            //.formatFileName("%sController")

                            //entity配置
                            .entityBuilder()
                            .disableSerialVersionUID()
                            .enableChainModel()
                            .enableLombok()
                            .enableRemoveIsPrefix()
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel)
                            //基础BaseEntity
                            .superClass(SUPER_ENTITY_CLASS)
                            //添加父类公共字段,会自动去除.与BaseEntity结合使用
                            .addSuperEntityColumns(SUPER_ENTITY_COLUMNS);
                    //.formatFileName("%sEntity");
                });
        //执行
        fastAutoGenerator.execute();
    }


}
