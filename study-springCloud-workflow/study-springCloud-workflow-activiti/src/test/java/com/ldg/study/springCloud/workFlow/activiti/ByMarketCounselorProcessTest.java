package com.ldg.study.springCloud.workFlow.activiti;


import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiDeployUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessInstanceUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessTaskUtil;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Test
    public void deploy() {
        //部署成功后，数据库中生成部署信息 和 流程定义数据
        Deployment deployment = deployUtil.deployment("process/subscribeCustomerProcess/ByMarketCounselorProcess.bpmn", "市场顾问发起预约客服申请");

        StringBuilder sb = new StringBuilder("部署信息：")
                .append("\r\n   ").append("id       : ").append(deployment.getId())
                .append("\r\n   ").append("key      : ").append(deployment.getKey())
                .append("\r\n   ").append("name     : ").append(deployment.getName())
                .append("\r\n   ").append("tenantId     : ").append(deployment.getTenantId())
                .append("\r\n   ").append("category     : ").append(deployment.getCategory())
                .append("\r\n   ").append("deploy time      : ").append(deployment.getDeploymentTime())
                .append("\r\n################################################################");

        System.out.println(sb.toString());
    }

    /**
     * 创建一个新的流程实例
     * <pre>
     *     1、ByKey : 以流程定义最新版本创建实例
     * </pre>
     */
    @Test
    public void startProcessInstanceByKey() {
        //变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUserId", "apply-001");
        variables.put("proviUserIds", "provie-001");
        ExecutionEntity instance = processInstanceUtil.startExecutionByKey("marketCounselorSubscribeCustomerProcess", "DD001", variables);
        printProcessInstance(instance);
    }

    /**
     * 创建一个新的流程实例
     * <pre>
     *     1、ById : 指定流程定义版本创建实例
     * </pre>
     */
    @Test
    public void startProcessInstanceById() {
        //数据库中已生成的流程定义Id，查看表“ACT_RE_PROCDEF”
        //查询流程定义Id
        ProcessDefinition processDefinition = deployUtil.findDefinitionId("1");//需动态配置

        //变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUserId", "apply-001");
        variables.put("proviUserIds", "provie-001");
        ExecutionEntity instance = processInstanceUtil.startExecuteionById(processDefinition.getId(), "DD002", variables);

        printProcessInstance(instance);
    }

    private void printProcessInstance(ProcessInstance instance) {
        //instanceId 需回写到业务数据库中，方便查询

        StringBuilder sb = new StringBuilder("启动实例：")
                .append("\r\n   ").append("id       : ").append(instance.getId())
                .append("\r\n   ").append("definitionId      : ").append(instance.getProcessDefinitionId())
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

    @Test
    public void findMyTask() {
        String myId = "apply-001";
        List<Task> taskes = processTaskUtil.finds(myId);
        if (CollectionUtils.isNotEmpty(taskes)) {
            for (Task task : taskes) {
                StringBuilder sb = new StringBuilder("我的任务：")
                        .append("\r\n   ").append("id       : ").append(task.getId())
                        .append("\r\n   ").append("assignee      : ").append(task.getAssignee())
                        .append("\r\n   ").append("name     : ").append(task.getName())
                        .append("\r\n   ").append("createTime     : ").append(task.getCreateTime())
                        .append("\r\n   ").append("instanceId     : ").append(task.getProcessInstanceId())
                        .append("\r\n################################################################");

                System.out.println(sb.toString());
            }
        }
    }

    @Test
    public void complete() {
        //1、先验证用户是否有权限进行完成
        //根据“登录用户Id”和taskId检查是否有权限操作

        //2、标记任务为完成
        processTaskUtil.complete("2507", Collections.EMPTY_MAP);
    }

    @Test
    public void showInstance(){
        ProcessInstance processInstance = processInstanceUtil.find("2501");

        printProcessInstance(processInstance);
    }
}
