package com.ldg.study.springCloud.workFlow.activiti.utils;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Activiti Process Instance 流程实例工具类
 *
 * @author： ldg
 * @create date： 2018/9/30
 */
@Component
public class ActivitiProcessInstanceUtil {

    /**
     * RuntimeService是activiti的流程执行服务类。可以从这个服务类中获取很多关于流程执行相关的信息，如执行管理，包括启动、推进、删除流程实例等操作。
     */
    @Resource
    private RuntimeService runtimeService;

    /**
     * 启动一个流程
     *
     * @param processDefinitionId 流程定义的Id
     * @param variables           启动流程的变量
     */
    public ProcessInstance start(String processDefinitionId, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceById(processDefinitionId, variables);
    }
}
