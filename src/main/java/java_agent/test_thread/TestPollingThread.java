package java_agent.test_thread;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestPollingThread {
    private static final ScheduledExecutorService schService = Executors.newScheduledThreadPool(1);

    private final Logger logger = LoggerFactory.getLogger(TestPollingThread.class);

   // consumer (using polling)
    public void processEventThread(Instrumentation inst) {
        schService.scheduleAtFixedRate(() -> {
            logger.info("ProcessFileEventThread polling...");
            /*
            Class<?> findClass = null;
            try {
                findClass = Class.forName("com.config.MyConfig");
                logger.info("Class forName : {}", findClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }*/

        },5000L, 10000L, TimeUnit.MILLISECONDS);
    }
}
