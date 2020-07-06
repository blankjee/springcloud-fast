package org.blankjee.cloudfast.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * @Author blankjee
 * @Date 2020/7/6 12:00
 */
@Configuration
public class ActivitiConfig {
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private HikariDataSource hikariDataSource;
    @Resource
    private ActivitiIdGenerate activitiIdGenerate;

    public ProcessEngineConfigurationImpl getProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // ID生成策略
        processEngineConfiguration.setIdGenerator(activitiIdGenerate);
        // 设置DbSqlSessionFactory的UUIDGenerator，否则流程ID，任务ID，实例ID依然是用DbIdGenerator生成
        processEngineConfiguration.getDbSqlSessionFactory().setIdGenerator(activitiIdGenerate);
        // 设置流程图片字体防止中文乱码
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        return processEngineConfiguration;
    }
}
