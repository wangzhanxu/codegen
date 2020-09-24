package com.mokkr.codegen;

import com.baomidou.mybatisplus.annotation.IdType;

public class CodeGenMain {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String[] STRATEGY_INCLUDES = new String[]{};
    private static final String PACKAGE_CONFIG_PARENT = "com.mokkr";
    private static final String PACKAGE_CONFIG_MODULE_NAME = "user";
    private static final String GLOBAL_CONFIG_AUTHOR = "wangzhanxu";
    private static final IdType GLOBAL_CONFIG_ID_TYPE = IdType.ID_WORKER_STR;

    public static void main(String[] args) {
        new CodeGen()
                .setDataSourceConfigJdbcUrl(JDBC_URL)
                .setDataSourceConfigUsername(DATABASE_USERNAME)
                .setDataSourceConfigPassword(DATABASE_PASSWORD)
                .setStrategyConfigIncludes(STRATEGY_INCLUDES)
                .setPackageConfigParent(PACKAGE_CONFIG_PARENT)
                .setPackageConfigModuleName(PACKAGE_CONFIG_MODULE_NAME)
                .setGlobalConfigAuthor(GLOBAL_CONFIG_AUTHOR)
                .setGlobalConfigIdType(GLOBAL_CONFIG_ID_TYPE)
                .doGenerate();
    }
}
