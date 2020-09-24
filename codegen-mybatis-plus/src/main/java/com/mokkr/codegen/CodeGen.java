package com.mokkr.codegen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Setter
public class CodeGen {
    private String dataSourceConfigJdbcUrl;
    private String dataSourceConfigUsername;
    private String dataSourceConfigPassword;
    private String[] strategyConfigIncludes;
    private String packageConfigParent;
    private String packageConfigModuleName;
    private String globalConfigOutputDir = "/codegen/" + UUID.randomUUID().toString();
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
        // 通过InjectionConfig自定义输出
        templateConfig.setXml(null);
        templateConfig.setController(null);
        return templateConfig;
    }

    private GlobalConfig createGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(globalConfigOutputDir);
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
                TableInfo tableInfo = (TableInfo) objectMap.get("table");
                String tableAlias = "t" + tableInfo.getEntityName();
                String aliasFieldNames = getAliasFieldNames(tableAlias, tableInfo.getFields());
                objectMap.put("tableAlias", tableAlias);
                objectMap.put("aliasFieldNames", aliasFieldNames);
                return objectMap;
            }

            private String getAliasFieldNames(String tableAlias, List<TableField> tableFields) {
                StringBuilder sb = new StringBuilder();
                IntStream.range(0, tableFields.size()).forEach(i -> {
                    TableField fd = tableFields.get(i);
                    if (i == tableFields.size() - 1) {
                        sb.append(tableAlias + "." + fd.getName());
                    } else {
                        sb.append(tableAlias + "." + fd.getName()).append(", ");
                    }
                });
                return sb.toString();
            }
        };
        // 自定义输出配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        // freemarker模板
        String templatePath = "/templates/mapper-ex.xml.ftl";
        // 自定义配置会被优先输出
        fileOutConfigs.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return String.format(globalConfigOutputDir + "/mapper/xml/%sMapper.xml", tableInfo.getEntityName());
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);
        return injectionConfig;
    }
}
