package cn.ok;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PROJECT: java-demo
 * PACKAGE: cn.ok
 * Created by Kyou on 2017/6/25.
 */
public class BillTask implements Runnable {
    private static final Logger _log = LogManager.getLogger();
    private Bill bill;

    BillTask(Bill bill) {
        this.bill = bill;
    }

    @Override
    public void run() {
        try {
            //TODO do your business here.
            _log.info(bill);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            _log.error(e);
        }
    }
}