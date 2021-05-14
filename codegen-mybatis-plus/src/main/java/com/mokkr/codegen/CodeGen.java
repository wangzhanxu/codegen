package com.mokkr.codegen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.UUID;

@Setter
@Accessors(chain = true)
public class CodeGen {
    private String dataSourceConfigJdbcUrl;
    private String dataSourceConfigUsername;
    private String dataSourceConfigPassword;
    private String[] strategyConfigIncludes;
    private String packageConfigParent;
    private String packageConfigModuleName;
    private String globalConfigAuthor;
    private IdType globalConfigIdType;

    public void doGenerate() {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        // 数据源配置
        DataSourceConfig dataSourceConfig = createDataSourceConfig();
        autoGenerator.setDataSource(dataSourceConfig);
        // 数据库表配置
        StrategyConfig strategyConfig = createStrategyConfig();
        autoGenerator.setStrategy(strategyConfig);
        // 包名配置
        PackageConfig packageConfig = createPackageConfig();
        autoGenerator.setPackageInfo(packageConfig);
        // 模板配置
        TemplateConfig templateConfig = createTemplateConfig();
        autoGenerator.setTemplate(templateConfig);
        // 全局配置
        GlobalConfig globalConfig = createGlobalConfig();
        autoGenerator.setGlobalConfig(globalConfig);
        // 自定义配置
        InjectionConfig injectionConfig = createInjectionConfig();
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    private DataSourceConfig createDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(dataSourceConfigJdbcUrl);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(dataSourceConfigUsername);
        dataSourceConfig.setPassword(dataSourceConfigPassword);
        return dataSourceConfig;
    }

    private StrategyConfig createStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setTablePrefix("t_");
        strategyConfig.setInclude(strategyConfigIncludes);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        strategyConfig.setVersionFieldName("version");
        strategyConfig.setLogicDeleteFieldName("deleted");
        return strategyConfig;
    }

    private PackageConfig createPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageConfigParent);
        packageConfig.setModuleName(packageConfigModuleName);
        packageConfig.setEntity("po");
        return packageConfig;
    }

    private TemplateConfig createTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setController(null);
        return templateConfig;
    }

    private GlobalConfig createGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("/codegen/" + UUID.randomUUID().toString());
        globalConfig.setFileOverride(true);
        globalConfig.setOpen(true);
        globalConfig.setAuthor(globalConfigAuthor);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setIdType(globalConfigIdType);
        return globalConfig;
    }

    private InjectionConfig createInjectionConfig() {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // Nothing to do
            }

            @Override
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                return objectMap;
            }
        };
        return injectionConfig;
    }
}
