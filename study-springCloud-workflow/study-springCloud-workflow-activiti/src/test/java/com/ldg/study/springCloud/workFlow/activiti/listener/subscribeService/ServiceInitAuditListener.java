package com.ldg.study.springCloud.workFlow.activiti.listener.subscribeService;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author： ldg
 * @create date： 2018/10/11
 */
public class ServiceInitAuditListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setVariable("auditUserId", "customer-user-ldg-001", true);
    }
}
