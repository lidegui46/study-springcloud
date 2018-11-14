package com.ldg.study.springCloud.workFlow.activiti.utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 查找所有部署
     */
    public List<Deployment> findsDeploy() {
        return processEngine.getRepositoryService().createDeploymentQuery().list();
    }

    /**
     * 删除部署
     *
     * @param deploymentId 部署编号
     */
    public void deleteDeploymentAndInstance(String deploymentId) {
        deleteDeployment(deploymentId, true);
    }


    /**
     * 根据部署编号查找流程定义编号
     *
     * @param deployId 部署编号
     */
    public ProcessDefinition findDefinitionId(String deployId) {
        return processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(deployId).desc().singleResult();
    }

    /**
     * 根据部署编号查找流程定义编号
     *
     * @param deployId 部署编号
     */
    public List<ProcessDefinition> findDefinitionIds(String deployId) {
        return processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(deployId).desc().list();
    }
}
