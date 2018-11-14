package com.ldg.study.springCloud.workFlow.activiti;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

import java.util.List;

/**
 * @author： ldg
 * @create date： 2018/10/10
 */
public class RollbackFirstTaskCmd extends NeedsActiveTaskCmd<Void> {

    private String deletereason;

    private String targetActivityId;

    public String getDeletereason() {
        return deletereason;
    }

    public String getTargetActivityId() {
        return targetActivityId;
    }

    public RollbackFirstTaskCmd(String taskId, String targetActivityId, String deletereason) {
        super(taskId);
        deletereason = deletereason;
        targetActivityId = targetActivityId;
    }

    public Void execute(CommandContext commandContext, TaskEntity currentTask) {
        RollbackFirstTaskCmd cmd = (RollbackFirstTaskCmd)commandContext.getCommand();

        String processDefinitionId = currentTask.getProcessDefinitionId();
        String executionId = currentTask.getExecutionId();
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        taskEntityManager.deleteTask(currentTask, cmd.getDeletereason(), false, false);
        FlowNode firstUserTask = this.findFirstActivity(processDefinitionId,cmd.getTargetActivityId());
        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);
        // 获取目标节点的来源连线
        List<SequenceFlow> flows = firstUserTask.getIncomingFlows();
        if (flows == null || flows.isEmpty()) {
            throw new ActivitiException("回退错误，目标节点没有来源连线");
        }
        // 随便选一条连线来执行，时当前执行计划为，从连线流转到目标节点，实现跳转
        executionEntity.setCurrentFlowElement(flows.get(0));
        commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
        // executionEntity.setCurrentFlowElement(flowElement);
        // commandContext.getAgenda().planContinueProcessOperation(executionEntity);
        return null;
    }

    public String getSuspendedTaskException() {
        return "挂起的任务不能跳转";
    }

    /**
     * 获得第一个节点.
     */
    public FlowNode findFirstActivity(String processDefinitionId,String targetActivityId) {
        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        FlowElement flowElement = process.getFlowElementMap().get(targetActivityId);
        FlowNode startActivity = (FlowNode) flowElement;
        if (startActivity.getOutgoingFlows().size() != 1) {
            throw new IllegalStateException("start activity outgoing transitions cannot more than 1, now is : " + startActivity.getOutgoingFlows().size());
        }
        SequenceFlow sequenceFlow = startActivity.getOutgoingFlows().get(0);
        FlowNode targetActivity = (FlowNode) sequenceFlow.getTargetFlowElement();
        if (!(targetActivity instanceof UserTask)) {
            return null;
        }
        return targetActivity;

        /*Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        FlowElement flowElement = process.getInitialFlowElement();
        FlowNode startActivity = (FlowNode) flowElement;
        if (startActivity.getOutgoingFlows().size() != 1) {
            throw new IllegalStateException("start activity outgoing transitions cannot more than 1, now is : " + startActivity.getOutgoingFlows().size());
        }
        SequenceFlow sequenceFlow = startActivity.getOutgoingFlows().get(0);
        FlowNode targetActivity = (FlowNode) sequenceFlow.getTargetFlowElement();
        if (!(targetActivity instanceof UserTask)) {
            return null;
        }
        return targetActivity;*/
    }
}
