package com.example;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.example.entity.base.BaseEntity;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义复杂代码生成器-使用自定义模板
 * <p>
 * 目录结构
 *      controller
 *      service
 *          impl
 *      infrastructure--基础层
 *          persistence--持久层
 *              entity
 *              mapper
 *          repository--存储层
 *              impl
 * </p>
 *
 * @author MrWen
 **/
public class CustomComplexCodeGenerator {

    //======================== 路径相关的 start ========================
    /**
     * 项目当前路径,单体项目直接这样就可以。String projectPath = System.getProperty("user.dir");
     * 因为这里是在父项目下，需要往后移一下路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir") + "/spring-boot-mybatis-plus/spring-boot-mybatis-plus-generate";
    /**
     * 输出目录
     */
    private static final String OUT_PUT_DIR = PROJECT_PATH + "/src/main/java";
    /**
     * 包父路径
     */
    private static final String PACKAGE_PARENT_PATH = "com.example";
    /**
     * 模块名称,可以不填
     */
    private static final String MODULE_NAME = "";
    /**
     * mapper包路径
     */
    private static final String MAPPER_PACKAGE_PATH = "infrastructure.persistence.mapper";
    /**
     * entity包路径
     */
    private static final String ENTITY_PACKAGE_PATH = "infrastructure.persistence.entity";
    /**
     * repository包路径
     */
    private static final String REPOSITORY_PACKAGE_PATH = "infrastructure.repository";
    /**
     * repositoryImpl包路径
     */
    private static final String REPOSITORY_IMPL_PACKAGE_PATH = "infrastructure.repository.impl";
    /**
     * 设置mapperXml路径
     */
    private static final String MAPPER_XML_PATH = "/src/main/resources/mapper";
    //======================== 路径相关的 end ========================

    //======================== 数据库相关的 start ========================
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
     * 需要生成的表
     */
    private static final List<String> TABLE_LIST = Arrays.asList("t_admin");
    /**
     * 设置过滤表前缀
     */
    private static final String TABLE_PREFIX = "t_";
    //======================== 数据库相关的 end ========================

    //======================== 生成实体相关的 start ========================
    /**
     * 作者
     */
    private static final String AUTHOR = "MrWen";
    /**
     * entity父类
     */
    private static final Class<?> SUPER_ENTITY_CLASS = BaseEntity.class;
    /**
     * 父类字段(数据库字段列名)
     */
    private static final String[] SUPER_ENTITY_COLUMNS = {"create_time", "create_name", "update_time", "update_name", "version", "deleted", "tenant_id"};
    //======================== 生成实体相关的 end ========================


    public static void main(String[] args) {
        //自定义模板，可以走默认的
        FastAutoGenerator fastAutoGenerator = FastAutoGenerator
                //配置数据源
                .create(URL, USER_NAME, PASSWORD)
                //全局配置
                .globalConfig(CustomComplexCodeGenerator::globalConfig)
                //包配置
                .packageConfig(CustomComplexCodeGenerator::packageConfig)
                //策略配置
                .strategyConfig(CustomComplexCodeGenerator::strategyConfig)
                //=============================== 这里是走自定义模板 =============================
                //注入配置
                .injectionConfig(CustomComplexCodeGenerator::injectionConfig)
                //模板引擎配置,重写了outputCustomFile方法  需要导入对应的包 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new CustomFreemarkerTemplateEngine())
                // 模板设置
                .templateConfig(CustomComplexCodeGenerator::templateConfig);

        //执行
        fastAutoGenerator.execute();
    }

    private static void injectionConfig(InjectionConfig.Builder builder) {
        //输出文件之前消费者
        builder.beforeOutputFile((tableInfo, objectMap) -> {
                    //注入自定义service参数，如adminService
                    objectMap.put("myService", tableInfo.getServiceName().substring(1, 2).toLowerCase() + tableInfo.getServiceName().substring(2));
                    //注入自定义mapper参数，如adminMapper
                    objectMap.put("myMapper", tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1));
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());

                    //repository注入
                    String entityName = (String) objectMap.get("entity");
                    String repositoryName = "I" + entityName + "Repository";
                    String repositoryImplName = entityName + "RepositoryImpl";

                    objectMap.put("repositoryName", repositoryName);
                    objectMap.put("repositoryImplName", repositoryImplName);

                    String repositoryPackage = getFullPackagePath(REPOSITORY_PACKAGE_PATH);
                    String repositoryImplPackage = getFullPackagePath(REPOSITORY_IMPL_PACKAGE_PATH);

                    objectMap.put("repositoryPackage", repositoryPackage);
                    objectMap.put("repositoryImplPackage", repositoryImplPackage);

                    objectMap.put("customRepository", new CustomFileInfo(repositoryName + ".java", getFullFilePath(REPOSITORY_PACKAGE_PATH)));
                    objectMap.put("customRepositoryImpl", new CustomFileInfo(repositoryImplName + ".java", getFullFilePath(REPOSITORY_IMPL_PACKAGE_PATH)));

                    //repository 其它属性
                    objectMap.put("myRepository", repositoryName.substring(1, 2).toLowerCase() + repositoryName.substring(2));
                })

                //自定义配置Map对象,自定义配置模板文件
                .customFile(getCustomFile())
                .build();
    }

    private static void strategyConfig(StrategyConfig.Builder builder) {
        builder
                // 设置需要生成的表名
                .addInclude(TABLE_LIST)
                // 设置过滤表前缀
                .addTablePrefix(TABLE_PREFIX)

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
    }

    private static void packageConfig(PackageConfig.Builder builder) {
        builder
                // 设置父包名
                .parent(PACKAGE_PARENT_PATH)
                // 设置mapperXml生成路径,和mapper路径
                .mapper(MAPPER_PACKAGE_PATH)
                .entity(ENTITY_PACKAGE_PATH)
                .pathInfo(getCustomPathInfo());

        //设置父包模块名
        if (StrUtil.isNotBlank(MODULE_NAME)) {
            builder.moduleName(MODULE_NAME);
        }
    }

    /**
     * 获取自定义路径配置信息
     *
     * @return 自定义路径配置信息
     */
    private static Map<OutputFile, String> getCustomPathInfo() {
        Map<OutputFile, String> customPathInfoMap = new HashMap<>();
        customPathInfoMap.put(OutputFile.mapper, getFullFilePath(MAPPER_PACKAGE_PATH));
        customPathInfoMap.put(OutputFile.entity, getFullFilePath(ENTITY_PACKAGE_PATH));
        customPathInfoMap.put(OutputFile.mapperXml, PROJECT_PATH + MAPPER_XML_PATH);
        return customPathInfoMap;
    }

    private static void globalConfig(GlobalConfig.Builder builder) {
        builder
                // 设置作者
                .author(AUTHOR)
                // 开启 swagger 模式
                .enableSwagger()
                //禁止打开输出目录
                .disableOpenDir()
                //时间格式， 默认LocalDate
                .dateType(DateType.TIME_PACK)
                // 覆盖已生成文件
                .fileOverride()
                // 指定输出目录
                .outputDir(OUT_PUT_DIR);
    }


    /**
     * 获取 完整包路径
     *
     * @param relativePath 相对路径：infrastructure.persistence.entity
     * @return com.example.infrastructure.persistence.entity
     */
    private static String getFullPackagePath(String relativePath) {
        //todo module
        if (StrUtil.isNotBlank(MODULE_NAME)) {
            return PACKAGE_PARENT_PATH + "." + MODULE_NAME + "." + relativePath;
        } else {
            return PACKAGE_PARENT_PATH + "." + relativePath;
        }
    }

    /**
     * 获取 完整文件路径
     *
     * @param relativePath 相对路径：infrastructure.persistence.entity
     * @return OUT_PUT_DIR+/com/example/infrastructure/persistence/entity
     */
    private static String getFullFilePath(String relativePath) {
        return (OUT_PUT_DIR + File.separatorChar + getFullPackagePath(relativePath)).replaceAll("\\.", "/");
    }

    /**
     * 自定义配置模板文件
     *
     * @return key:文件类型（objectMap中的key，获取对应属性）  value:模板路径
     */
    private static Map<String, String> getCustomFile() {
        Map<String, String> customFile = new HashMap<>();
        customFile.put("customRepository", "/customComplexTemplates/repository.java.ftl");
        customFile.put("customRepositoryImpl", "/customComplexTemplates/repositoryImpl.java.ftl");
        return customFile;
    }

    /**
     * 模板设置
     */
    private static TemplateConfig.Builder templateConfig(TemplateConfig.Builder builder) {
        return builder.controller("/customComplexTemplates/controller.java")
                .service("/customComplexTemplates/service.java")
                .serviceImpl("/customComplexTemplates/serviceImpl.java")
                .mapper("/customComplexTemplates/mapper.java")
                .mapperXml("/customComplexTemplates/mapper.xml")
                .entity("/customComplexTemplates/entity.java");
    }
}
