package com.seagold.community.utils;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author xjh
 * @date 2022/11/15
 */
public class TransactionUtil {

    public static void doAfterTransaction(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new DoTransactionCompletion(runnable));
        }
    }

    /**
     * 业务代码使用方法
     */
    @Transactional(rollbackFor = Throwable.class)
    public void doTx() {
        // start tx

        TransactionUtil.doAfterTransaction(() -> {
            // send mq...  rpc...
        });

        // end tx
    }
}

class DoTransactionCompletion implements TransactionSynchronization {

    private final Runnable runnable;

    public DoTransactionCompletion(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void afterCompletion(int status) {
        if (status == TransactionSynchronization.STATUS_COMMITTED) {
            this.runnable.run();
        }
    }
}