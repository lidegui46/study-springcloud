package com.ldg.study.springCloud.workFlow.activiti;


import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiDeployUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessInstanceUtil;
import com.ldg.study.springCloud.workFlow.activiti.utils.ActivitiProcessTaskUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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

    @Test
    public void startProcessInstance() {
        //数据库中已生成的流程定义Id，查看表“ACT_RE_PROCDEF”
        String processDefinitionId = "marketCounselorSubscribeCustomerProcess:1:4";
        //变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUserIds", "ldg-001,ll-001");
        ProcessInstance instance = processInstanceUtil.start(processDefinitionId, variables);

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
                .append("\r\n################################################################");

        System.out.println(sb.toString());
    }

    @Test
    public void findMyTask() {
        String myId = "ldg-001";
        List<Task> taskes = processTaskUtil.findCandidateUser(myId);
        if (taskes != null && taskes.size() > 0) {
            for (Task task : taskes) {
                StringBuilder sb = new StringBuilder("启动实例：")
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
}
