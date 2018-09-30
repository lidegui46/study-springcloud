package com.ldg.study.springCloud.workFlow.activiti.utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Activiti工具类
 *
 * @author： ldg
 * @create date： 2018/9/30
 */
@Component
public class ActivitiDeployUtil {


    /**
     * 工作流核心引擎对象
     */
    @Resource
    private ProcessEngine processEngine;

    /**
     * 部署流程
     *
     * @param bpmnResourcePath 资源文件路径
     * @param deployName       部署名称
     */
    public Deployment deployment(String bpmnResourcePath, String deployName) {
        /*if (StringUtils.isEmpty(bpmnResourcePath) || StringUtils.isEmpty(deployName)) {
            throw new Exception("bpmnResource or deployName is null");
        }*/
        return processEngine.getRepositoryService()
                .createDeployment()
                .name(deployName)
                .addClasspathResource(bpmnResourcePath)
                .deploy();
    }

    /**
     * 删除部署
     *
     * @param deploymentId 部署编号
     * @param cascade      是否删除已部署的实例
     */
    public void deleteDeployment(String deploymentId, boolean cascade) {
        processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
    }

    /**
     * 删除部署
     *
     * @param deploymentId 部署编号
     */
    public void deleteDeploymentAndInstance(String deploymentId) {
        deleteDeployment(deploymentId, true);
    }
}
