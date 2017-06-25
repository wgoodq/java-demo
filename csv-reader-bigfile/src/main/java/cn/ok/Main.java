package cn.ok;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * PROJECT: java-demo
 * PACKAGE: cn.ok
 * Created by Kyou on 2017/6/25 7:29.
 */
public class Main {
    private static final Logger _log = LogManager.getLogger();

    public static void main(String[] args) {
        long t_start, t_end;
        try {
            t_start = System.currentTimeMillis();

            BillCsvReaderMT billCsvReaderMT = new BillCsvReaderMT();
            // get the
            String classPath = Main.class.getResource("/").getPath();
            String filePath = classPath + "../../../resources/main/123.csv";
            Files.asCharSource(new File(filePath), Charsets.UTF_8).readLines(billCsvReaderMT);
            billCsvReaderMT.close();

            t_end = System.currentTimeMillis();
            _log.info("multi Thread total used: {}ms", t_end - t_start);
        } catch (IOException e) {
            _log.entry(e);
        }
    }
}
