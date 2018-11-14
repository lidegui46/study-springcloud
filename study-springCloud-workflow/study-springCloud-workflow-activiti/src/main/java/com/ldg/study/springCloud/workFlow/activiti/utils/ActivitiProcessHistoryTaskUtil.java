package com.ldg.study.springCloud.workFlow.activiti.utils;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Activiti Process Task 流程任务工具类
 *
 * @author： ldg
 * @create date： 2018/9/30
 */
@Component
public class ActivitiProcessHistoryTaskUtil {

    @Resource
    private HistoryService historyService;

    public List<HistoricTaskInstance> findTasks(String businessKey) {
        return historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).orderByTaskCreateTime().asc().list();
    }
}
