package cn.ok;

import com.google.common.base.Splitter;
import com.google.common.io.LineProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * PROJECT: java-demo
 * PACKAGE: cn.ok
 * Created by Kyou on 2017/6/25.
 */
public class BillCsvReaderMT implements LineProcessor<String> {
    private static final Logger _log = LogManager.getLogger();
    private static boolean titleFlag = true;
    private int idx_id = -1, idx_billNo = -1, idx_price = -1, idx_hsCode = -1, idx_weight = -1, idx_date = -1;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

    @Override
    public boolean processLine(@Nonnull String line) throws IOException {
        if (line.isEmpty()) return true;
        try {
            if (titleFlag) {
                indexColumn(line);
                titleFlag = false;
                return true;
            }

            List<String> values = Splitter.on(",").trimResults().splitToList(line);

            Bill bill = new Bill();
            bill.setId(values.get(idx_id));
            bill.setBillNo(values.get(idx_billNo));
            bill.setPrice(Double.valueOf(values.get(idx_price)));
            bill.setHsCode(values.get(idx_hsCode));
            bill.setWeight(Double.valueOf(values.get(idx_weight)));
            bill.setCreateDate(values.get(idx_date));

            BillTask billTask = new BillTask(bill);
            _log.info("线程池中线程数目：{}，队列中等待执行的任务数目：{}，已执行玩别的任务数目：{}", executor.getPoolSize(), executor.getQueue().size(), executor.getCompletedTaskCount());

            while (executor.getPoolSize() == executor.getMaximumPoolSize()) {
                _log.debug("主线程睡眠，等待空位。");
                Thread.sleep(100);
            }
            executor.execute(billTask);

        } catch (Exception e) {
            _log.error(e);
            return false;
        }
        return true;
    }

    @Override
    public String getResult() {
        return null;
    }

    void close() {
        executor.shutdown();
    }

    private void indexColumn(String line) throws Exception {
        List<String> columns = Splitter.on(",").trimResults().splitToList(line);
        int i = 0;

        // number the columns
        for (String column : columns) {
            String temp = column.toUpperCase();
            switch (temp) {
                case "ID":
                    idx_id = i;
                    break;
                case "BILL_NO":
                    idx_billNo = i;
                    break;
                case "PRICE":
                    idx_price = i;
                    break;
                case "H_S_CODE":
                    idx_hsCode = i;
                    break;
                case "WEIGHT":
                    idx_weight = i;
                    break;
                case "CREATE_DATE":
                    idx_date = i;
                    break;
            }
            i++;
        }

        // check unnumbered column
        if (idx_id < 0) {
            throw new Exception("there is no ID column in the file.");
        }
        if (idx_billNo < 0) {
            throw new Exception("there is no BILL_NO column in the file.");
        }
        if (idx_hsCode < 0) {
            throw new Exception("there is no H_S_CODE column in the file.");
        }
        if (idx_price < 0) {
            throw new Exception("there is no PRICE column in the file.");
        }
        if (idx_weight < 0) {
            throw new Exception("there is no WEIGHT column in the file.");
        }
        if (idx_date < 0) {
            throw new Exception("there is no CREATE_DATE column in the file.");
        }
    }

    @Override
    public String toString() {
        return "BillCsvReader{" +
                "idx_id=" + idx_id +
                ", idx_billNo=" + idx_billNo +
                ", idx_price=" + idx_price +
                ", idx_hsCode=" + idx_hsCode +
                ", idx_weight=" + idx_weight +
                ", idx_date=" + idx_date +
                '}';
    }
}
