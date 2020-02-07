package com.corereach.communication;


import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @Description: 导入FastDFS-Client组件,EnableMBeanExport注解解决jmx重复注册bean的问题
 * @Author ga.zhang
 * @Date 2020/1/15 10:16
 * @Version V1.0
 **/
@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastdfsImporter {
    // 导入依赖组件
}
