package com.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.entity.base.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 代码生成器
 * </p>
 *
 * @author MrWen
 **/
public class CodeGenerator {

    public static void main(String[] args) {
        //包父路径
        String packageParentPath = "com.example";
        //设置作者
        String author = "MrWen";
        //需要生成的表
        List<String> tableList = Arrays.asList("t_admin", "t_user");
        //设置过滤表前缀
        String tablePrefix = "t_";

        //项目当前路径,单体项目直接这样就可以。
//        String projectPath = System.getProperty("user.dir");
        //因为这里是在父项目下，需要往后移一下路径
        String projectPath = System.getProperty("user.dir") + "/spring-boot-mybatis-plus/spring-boot-mybatis-plus-generate";

        FastAutoGenerator fastAutoGenerator = FastAutoGenerator
                //配置数据源
                .create("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8",
                        "root", "root")

                //全局配置
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()//禁止打开输出目录
                            .dateType(DateType.ONLY_DATE)//时间格式，只使用 java.util.date 代替  默认LocalDate
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })

                //包配置
                .packageConfig(builder -> {
                    builder.parent(packageParentPath) // 设置父包名
//                            .moduleName("robot") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })

                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tableList) // 设置需要生成的表名
                            .addTablePrefix(tablePrefix) // 设置过滤表前缀

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
                            .superClass(BaseEntity.class)
                            //添加父类公共字段,会自动去除.与BaseEntity结合使用
                            .addSuperEntityColumns("create_time", "create_name", "update_time", "update_name", "version", "deleted", "tenant_id");
                    //.formatFileName("%sEntity");
                });


        //=============================== 这里是走自定义模板，不需要可以直接注释了 =============================
        //注入配置
        fastAutoGenerator.injectionConfig(builder -> {
                    //输出文件之前消费者
                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                                //注入自定义service参数，如adminService
                                objectMap.put("myService", tableInfo.getServiceName().substring(1, 2).toLowerCase() + tableInfo.getServiceName().substring(2));
                                //注入自定义mapper参数，如adminMapper
                                objectMap.put("myMapper", tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1));
                                System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                            })

                            //todo 自定义配置Map对象,自定义配置模板文件
//                            .customMap(Collections.singletonMap("test", "baomidou"))
//                            .customFile(Collections.singletonMap("test.txt", "/templates/test.vm"))
                            .build();
                })
                //模板引擎配置,需要导入对应的包 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> {
                    //自定义模板，可以走默认的
                    builder.controller("/customTemplates/controller.java")
                            .service("/customTemplates/service.java")
                            .serviceImpl("/customTemplates/serviceImpl.java")
                            .mapper("/customTemplates/mapper.java")
                            .mapperXml("/customTemplates/mapper.xml")
                            .entity("/customTemplates/entity.java");
                });

        //执行
        fastAutoGenerator.execute();
    }


}
