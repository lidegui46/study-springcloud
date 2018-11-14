package com.ldg.study.springCloud.workFlow.activiti.utils;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
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
public class ActivitiProcessTaskUtil {

    /**
     * TaskService是activiti的任务服务类。可以从这个类中获取任务的信息
     */
    @Autowired
    private TaskService taskService;

    /**
     * 完成任务
     *
     * @param taskId    任务编号
     * @param variables 启动流程的变量
     */
    public void complete(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    public Task findById(String taskId) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskId(taskId);
        if (ObjectUtils.isEmpty(taskQuery)) {
            return null;
        }
        return taskQuery.singleResult();
    }

    public List<Task> finds(String userId) {
        List<Task> list = taskService
                .createTaskQuery()
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        return list;
    }

    public List<Task> findAssignees(String userId) {
        List<Task> list = taskService
                .createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        return list;
    }

    public List<Task> findCandidateUser(String userId) {
        List<Task> list = taskService
                .createTaskQuery()
                .taskCandidateUser(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        return list;
    }

    public List<Task> findTasksByBusinessKey(String businessKey) {
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
    }

    /**
     * 根据业务Key获取待执行的任务
     *
     * @param businessKey
     * @return
     */
    public Task findTaskByBusinessKey(String businessKey) {
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
    }
}
