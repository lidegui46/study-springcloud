package com.ldg.study.springCloud.workFlow.activiti;


import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiDeployUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessHistoryTaskUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessInstanceUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessTaskUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author： ldg
 * @create date： 2018/9/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ByMarketCounselorProcessTest {

    @Resource
    private ActivitiDeployUtil deployUtil;

    @Resource
    private ActivitiProcessInstanceUtil processInstanceUtil;

    @Resource
    private ActivitiProcessTaskUtil processTaskUtil;

    @Resource
    private ActivitiProcessHistoryTaskUtil activitiProcessHistoryTaskUtil;

    @Resource
    private ProcessEngine processEngine;

    @Test
    public void createTable() {
        //DbSchemaCreate.main(null);
        /*ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault()
                .setDatabaseSchemaUpdate("create")
                .buildProcessEngine();*/
    }

    /**
     * 部署并生成流程定义
     * 发生域：新建流程 或 流程发生变化 需要执行
     */
    public String deploy(String bpmnFile, String deployName) {
        //部署成功后，数据库中生成部署信息 和 流程定义数据
        Deployment deployment = deployUtil.deployment(bpmnFile, deployName);

        StringBuilder sb = new StringBuilder("部署信息：")
                .append("\r\n   ").append("id       : ").append(deployment.getId())
                .append("\r\n   ").append("key      : ").append(deployment.getKey())
                .append("\r\n   ").append("name     : ").append(deployment.getName())
                .append("\r\n   ").append("tenantId     : ").append(deployment.getTenantId())
                .append("\r\n   ").append("category     : ").append(deployment.getCategory())
                .append("\r\n   ").append("deploy time      : ").append(deployment.getDeploymentTime())
                .append("\r\n################################################################");

        System.out.println(sb.toString());
        return deployment.getId();
    }

    public void deleteDeploy() {
        List<Deployment> deployments = deployUtil.findsDeploy();
        for (Deployment deploy : deployments) {
            deployUtil.deleteDeployment(deploy.getId(), true);
        }
    }

    /**
     * 创建一个新的流程实例
     * <pre>
     *     1、ByKey : 以流程定义最新版本创建实例
     * </pre>
     */
    public String startProcessInstanceByKey(String DefinitionKey, String businessKey, Map<String, Object> variables) {
        ExecutionEntity instance = processInstanceUtil.startExecutionByKey(DefinitionKey, businessKey, variables);
        printProcessInstance(instance);
        return instance.getId();
    }

    /**
     * 创建一个新的流程实例
     * <pre>
     *     1、ById : 指定流程定义版本创建实例
     * </pre>
     */
    public String startProcessInstanceById(String deployId, String businessKey) {
        //数据库中已生成的流程定义Id，查看表“ACT_RE_PROCDEF”
        //查询流程定义Id
        ProcessDefinition processDefinition = deployUtil.findDefinitionId(deployId);//需动态配置

        //变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUserId", "apply-001");
        variables.put("auditUserId", "audit-001");
        variables.put("reAuditUserId", "reaudit-001");
        ExecutionEntity instance = processInstanceUtil.startExecuteionById(processDefinition.getId(), businessKey, variables);

        printProcessInstance(instance);
        return instance.getId();
    }

    private void printProcessInstance(ProcessInstance instance) {
        //instanceId 需回写到业务数据库中，方便查询

        StringBuilder sb = new StringBuilder("启动实例：")
                .append("\r\n   ").append("id       : ").append(instance.getId())
                .append("\r\n   ").append("definitionId      : ").append(instance.getProcessDefinitionId())
                .append("\r\n   ").append("instanceId      : ").append(instance.getProcessInstanceId())
                .append("\r\n   ").append("currentActivityId      : ").append(instance.getActivityId())
                .append("\r\n   ").append("name     : ").append(instance.getName())
                .append("\r\n   ").append("tenantId     : ").append(instance.getTenantId())
                .append("\r\n   ").append("businessKey     : ").append(instance.getBusinessKey())
                .append("\r\n   ").append("deployId      : ").append(instance.getDeploymentId())
                .append("\r\n   ").append("variables      : ").append(instance.getProcessVariables())
                .append("\r\n   ").append("startTime      : ").append(instance.getStartTime())
                .append("\r\n   ").append("startUserId      : ").append(instance.getStartUserId())
                //.append("\r\n   ").append("tasks      : ").append(instance.getTasks())
                .append("\r\n################################################################");

        System.out.println(sb.toString());
    }

    /**
     * 根据人员编号获取待任务
     *
     * @param userId 待办人
     */
    public List<Task> findMyTask(String userId) {
        List<Task> taskes = processTaskUtil.finds(userId);
        if (CollectionUtils.isNotEmpty(taskes)) {
            for (Task task : taskes) {
                printTasking(task);
            }
        }
        return taskes;
    }

    public void changeCandidateUser(String oldUserId, String newUserId) {
        List<Task> taskes = findMyTask(oldUserId);
        if (CollectionUtils.isNotEmpty(taskes)) {
            for (Task task : taskes) {
                //task.setAssignee(newUserId);
                processEngine.getTaskService().setAssignee(task.getId(), newUserId);
                //processEngine.getTaskService().addCandidateUser(task.getId(), newUserId);
                //processEngine.getTaskService().deleteCandidateUser(task.getId(),oldUserId);

                StringBuilder sb = new StringBuilder("用户变更完成：")
                        .append("\r\n   ").append("id       : ").append(task.getId())
                        .append("\r\n   ").append("old userId      : ").append(task.getAssignee())
                        .append("\r\n   ").append("new userId      : ").append(newUserId)
                        .append("\r\n################################################################");

                System.out.println(sb.toString());
            }
        }
    }

    public Task findByBusinessKey(String businessKey) {
        //"DD001"
        Task task = processTaskUtil.findTaskByBusinessKey(businessKey);
        printTasking(task);
        return task;
    }

    private void printTasking(Task task) {
        StringBuilder sb = new StringBuilder("我待处理的任务：")
                .append("\r\n   ").append("id       : ").append(task.getId())
                .append("\r\n   ").append("assignee      : ").append(task.getAssignee())
                .append("\r\n   ").append("name     : ").append(task.getName())
                .append("\r\n   ").append("createTime     : ").append(task.getCreateTime())
                .append("\r\n   ").append("instanceId     : ").append(task.getProcessInstanceId())
                .append("\r\n################################################################");

        System.out.println(sb.toString());
    }


    /**
     * 获取已执行和正在执行的任务
     *
     * @param businessKey 业务编号
     * @return
     */
    public List<HistoricTaskInstance> findHistoryTask(String businessKey) {
        List<HistoricTaskInstance> historyTasks = activitiProcessHistoryTaskUtil.findTasks(businessKey);
        for (HistoricTaskInstance task : historyTasks) {
            StringBuilder sb = new StringBuilder("历史任务：")
                    .append("\r\n   ").append("id       : ").append(task.getId())
                    .append("\r\n   ").append("assignee      : ").append(task.getAssignee())
                    .append("\r\n   ").append("name     : ").append(task.getName())
                    .append("\r\n   ").append("createTime     : ").append(task.getCreateTime())
                    .append("\r\n   ").append("instanceId     : ").append(task.getProcessInstanceId())
                    .append("\r\n   ").append("definitionId     : ").append(task.getProcessDefinitionId())
                    .append("\r\n################################################################");

            System.out.println(sb.toString());
        }
        return historyTasks;
    }

    /**
     * 完成任务
     *
     * @param taskId 任务编号
     * @param varMap 下一个任务需要的参数
     */
    public void complete(String taskId, Map<String, Object> varMap) {
        //1、先验证用户是否有权限进行完成
        //根据“登录用户Id”和taskId检查是否有权限操作

        //2、标记任务为完成
        processTaskUtil.complete(taskId, varMap); //无参

        StringBuilder sb = new StringBuilder("完成任务：")
                .append("\r\n   ").append("id       : ").append(taskId)
                .append("\r\n   ").append("varMap      : ").append(varMap)
                .append("\r\n################################################################");

        System.out.println(sb.toString());

        /*Map<String, Object> varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit","fail");
        //varMap.put("reAudit","fail");
        processTaskUtil.complete(taskId, varMap);//有参*/
    }

    public void showInstance(String instanceId) {
        ProcessInstance processInstance = processInstanceUtil.find(instanceId);

        printProcessInstance(processInstance);
    }

    /**
     * 获取流程变量
     *
     * @param executionId 流向编号
     */
    public void showVariableInstance(String executionId) {
        Map<String, VariableInstance> variableInstances = processEngine.getRuntimeService().getVariableInstances(executionId);

        StringBuilder sb = new StringBuilder("变量：");
        for (Map.Entry entry : variableInstances.entrySet()) {
            sb.append("\r\n   ").append(entry.getKey()).append(" : ").append(entry.getValue());
        }
        sb.append("\r\n################################################################");

        System.out.println(sb.toString());
    }

    /**
     * 获取历史活动
     *
     * @param executionId 流向编号
     */
    public void showHistoryActivity(String executionId) {
        List<HistoricActivityInstance> historyTasks = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .executionId(executionId)
                .finished()
                .list();

        for (HistoricActivityInstance activity : historyTasks) {
            StringBuilder sb = new StringBuilder("历史活动：")
                    .append("\r\n   ").append("id       : ").append(activity.getId())
                    .append("\r\n   ").append("definitionId     : ").append(activity.getProcessDefinitionId())
                    .append("\r\n   ").append("instanceId     : ").append(activity.getProcessInstanceId())
                    .append("\r\n   ").append("taskId     : ").append(activity.getTaskId())
                    .append("\r\n   ").append("activityId     : ").append(activity.getActivityId())
                    .append("\r\n   ").append("executionId     : ").append(activity.getExecutionId())
                    .append("\r\n   ").append("assignee      : ").append(activity.getAssignee())
                    .append("\r\n   ").append("activityName     : ").append(activity.getActivityName())
                    .append("\r\n   ").append("startTime     : ").append(activity.getStartTime())
                    .append("\r\n   ").append("endTime     : ").append(activity.getEndTime())
                    .append("\r\n################################################################");

            System.out.println(sb.toString());
        }
    }

    /**
     * 当前待处理的下一个任务
     *
     * @param executionId 流向编号
     */
    public void showCurrentExecution(String executionId) {
        List<Execution> executionList = processEngine.getRuntimeService().createExecutionQuery().executionId(executionId).list();
        StringBuilder sb = new StringBuilder("变量：");
        for (Execution execution : executionList) {
            sb.append("\r\n   ").append("id").append(" : ").append(execution.getId());
            sb.append("\r\n   ").append("ActivityId").append(" : ").append(execution.getActivityId());
            sb.append("\r\n   ").append("Name").append(" : ").append(execution.getName());
            sb.append("\r\n   ").append("superExecutionId").append(" : ").append(execution.getSuperExecutionId());
        }
        sb.append("\r\n################################################################");

        System.out.println(sb.toString());


        /*Execution execution = processEngine.getRuntimeService().createExecutionQuery().executionId(executionId).singleResult();
        String activityId = execution.getActivityId();
        System.out.println("------->> activityId:" + activityId);*/
    }

    @Test
    public void getCurrentExecution_Flow() {
        processEngine.getManagementService().executeCommand(new RollbackFirstTaskCmd("25002", "customerInitAuth", "回退"));

        /*//获取当前待处理的任务
        Execution execution = processEngine.getRuntimeService().createExecutionQuery().executionId("2505").singleResult();

        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel("marketCounselorSubscribeCustomerProcess:1:4");
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(execution.getActivityId());
        //清除当前活动方向
        flowNode.getOutgoingFlows().clear();

        //获取指定的活动节点
        FlowNode sourceFlowNode_SubmitApply = (FlowNode) bpmnModel.getMainProcess().getFlowElement("submitApply");
        FlowNode targetFlowNode_customerApply = (FlowNode) bpmnModel.getMainProcess().getFlowElement("customerInitAuth");

        //新建活动方向
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(sourceFlowNode_SubmitApply);
        newSequenceFlow.setTargetFlowElement(targetFlowNode_customerApply);

        List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
        newSequenceFlowList.add(newSequenceFlow);

        flowNode.setOutgoingFlows(newSequenceFlowList);*/


        //Authentication.setAuthenticatedUserId(loginUser.getUsername());
        //processEngine.getTaskService().addComment(task.getId(), task.getProcessInstanceId(), "撤回");
    }

    /**
     * 流程：申请 -> 初审成功 -> 复审失败 -> 重新发起申请 -> 初审成功 -> 复审成功 -> 完成
     * 采用“用户任务”方式完成
     * 缺陷：
     * 用户任务“重新发起申请”是用户的待办项，而实际上申请失败后，用户可能不会操作此项
     * <p>
     * 处理机制：把用户待办任务全部查询出来后，如果业务是失败，则不显示在待办事项中
     */
    @Test
    public void AdviserApplyChannel() {
        String businessKey = "DD001";

        this.deleteDeploy();
        String deployId = this.deploy("process/AdviserApplyChannel.bpmn", "市场顾问申请渠道");

        Map<String, Object> instanceVarMap = new HashMap<>();
        instanceVarMap.put("applyUserId", "apply-001");
        instanceVarMap.put("auditUserId", "audit-001");
        instanceVarMap.put("reAuditUserId", "reaudit-001");
        String instanceId = this.startProcessInstanceByKey("marketCounselorSubscribeCustomerProcess", businessKey, instanceVarMap);

        //发起申请
        Task task = this.findByBusinessKey(businessKey);
        this.complete(task.getId(), Collections.EMPTY_MAP);

        //初审成功
        task = this.findByBusinessKey(businessKey);
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit","fail");
        this.complete(task.getId(), varMap);


        //复审失败
        task = this.findByBusinessKey(businessKey);
        varMap = new HashMap<>();
        //varMap.put("audit", "success");
        varMap.put("audit", "fail");
        this.complete(task.getId(), varMap);


        //复审发起申请申请
        task = this.findByBusinessKey(businessKey);
        this.complete(task.getId(), Collections.EMPTY_MAP);


        //初审成功
        task = this.findByBusinessKey(businessKey);
        varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit","fail");
        this.complete(task.getId(), varMap);


        //复审成功
        task = this.findByBusinessKey(businessKey);
        varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit","fail");
        this.complete(task.getId(), varMap);
    }

    /**
     * 流程1：申请 -> 初审成功 -> 复审失败 -> 完成
     * 流程2：复制“流程1”中的数据，重新创建一个新流程，再删除以前的流程
     * 采用“用户任务”方式完成
     * 缺陷：
     * 用户任务“重新发起申请”是用户的待办项，而实际上申请失败后，用户可能不会操作此项
     */
    @Test
    public void SubscribeService() {
        String businessKey = "DD002";

        this.deleteDeploy();
        String deployId = this.deploy("process/SubscribeService.bpmn", "市场顾问预约客服");

        Map<String, Object> instanceVarMap = new HashMap<>();
        instanceVarMap.put("applyUserId", "apply-001");
        instanceVarMap.put("auditUserId", "audit-001");
        instanceVarMap.put("reAuditUserId", "reaudit-001");
        instanceVarMap.put("reAuditFailToUserIds", "reaudit-001,apply-001");
        String instanceId = this.startProcessInstanceByKey("SubscribeService", businessKey, instanceVarMap);

        //发起申请
        Task task = this.findByBusinessKey(businessKey);
        this.complete(task.getId(), Collections.EMPTY_MAP);

        //初审成功
        task = this.findByBusinessKey(businessKey);
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit","fail");
        this.complete(task.getId(), varMap);


        //复审失败
        task = this.findByBusinessKey(businessKey);
        varMap = new HashMap<>();
        //varMap.put("audit", "success");
        varMap.put("audit", "fail");
        this.complete(task.getId(), varMap);


        //复审失败，顾问再次发起审批
        task = this.findByBusinessKey(businessKey);
        varMap = new HashMap<>();
        //varMap.put("audit", "success");
        varMap.put("reApplyToReAudit", "yes");
        varMap.put("reApplyToSucess", "");
        this.complete(task.getId(), varMap);


        //复审成功
        task = this.findByBusinessKey(businessKey);
        varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit", "fail");
        this.complete(task.getId(), varMap);
    }

    /**
     * 变更审批用户
     */
    @Test
    public void changeUser() {
        String businessKey = "DD003";

        this.deleteDeploy();
        String deployId = this.deploy("process/SubscribeService.bpmn", "市场顾问预约客服");

        Map<String, Object> instanceVarMap = new HashMap<>();
        instanceVarMap.put("applyUserId", "apply-001");
        instanceVarMap.put("auditUserId", "audit-001");
        instanceVarMap.put("reAuditUserId", "reaudit-001");
        instanceVarMap.put("reAuditFailToUserIds", "reaudit-001,apply-001");
        String instanceId = this.startProcessInstanceByKey("SubscribeService", businessKey, instanceVarMap);

        //发起申请
        Task task = this.findByBusinessKey(businessKey);
        this.complete(task.getId(), Collections.EMPTY_MAP);

        //初审成功
        task = this.findByBusinessKey(businessKey);
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("audit", "success");
        //varMap.put("audit","fail");
        this.complete(task.getId(), varMap);

        //变更用户
        changeCandidateUser("reaudit-001", "reaudit-002");

        //获取用户信息
        task = this.findByBusinessKey(businessKey);
    }

    @Test
    public void showTaskVariables() {
        //processEngine.getRuntimeService().getVariableInstances()
        String taskId = "157522";
        Map<String, VariableInstance> variableInstances = processEngine.getTaskService().getVariableInstances(taskId);
        for (Map.Entry<String, VariableInstance> entry : variableInstances.entrySet()) {
            VariableInstance value = entry.getValue();
            value.setTextValue(value.getTextValue() + ",TestId-001");
            System.out.println(value.getTextValue());
        }
        processEngine.getTaskService().setVariables(taskId, variableInstances);
    }

    /**
     * 设置流程变量
     */
    public void setVariables(){
        /*runtimeService.setVariable(executionId, variableName, value);
        runtimeService.setVariables(executionId, variables);

        taskService.setVariable(taskId, variableName, value);
        // 使用任务Id，和流程变量的名称，设置流程变量的值
        taskService.setVariables(taskId, variables);
        使用任务Id, 和Map集合设置流程变量，设置多个值

        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        启动流程实例的同时，可以设置流程变量，使用Map集合
        taskService.complete(taskId, variables);
        完成任务的同时，设置流程变量，使用Map集合*/
    }

    public void getVariables(){
        /** 获取流程变量 **/
       /* runtimeService.getVariable(executionId, variableName);
        使用执行对象Id和流程变量名称，获取值
        runtimeService.getVariables(executionId);
        使用执行对象Id，获取所有的流程变量，返回Map集合
        runtimeService.getVariables(executionId, variableNames);
        使用执行对象Id，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map中

        taskService.getVariable(taskId, variableName);
        使用任务Id和流程变量名称，获取值
        taskService.getVariables(taskId);
        使用任务Id，获取所有的流程变量，返回Map集合
        taskService.getVariables(taskId, variableNames);
        使用任务Id，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map中*/
    }
}
