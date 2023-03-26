package com.example;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

/**
 * <p>
 * 自定义Freemarker 模板引擎实现文件输出
 * </p>
 *
 * @author MrWen
 **/
public class CustomFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    @Override
    protected void outputCustomFile(@NotNull Map<String, String> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        customFile.forEach((key, value) -> {
            CustomFileInfo customFileInfo = (CustomFileInfo) objectMap.get(key);
            String fileName = String.format((customFileInfo.getFileDir() + File.separator + "%s"), customFileInfo.getFileName());
            outputFile(new File(fileName), objectMap, value);
        });
    }
}
