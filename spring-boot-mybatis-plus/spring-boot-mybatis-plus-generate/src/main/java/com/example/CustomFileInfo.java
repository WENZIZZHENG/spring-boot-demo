package com.example;

import lombok.Data;

/**
 * <p>
 * 自定义文件属性
 * </p>
 *
 * @author MrWen
 **/
@Data
public class CustomFileInfo {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 目录(相对路径)
     */
    private String fileDir;

    public CustomFileInfo(String fileName, String fileDir) {
        this.fileName = fileName;
        this.fileDir = fileDir;
    }
}
