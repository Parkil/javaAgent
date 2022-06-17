package java_agent.test_thread;

import compiler.CustomCompiler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.nio.file.Files;
import java.nio.file.Path;
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
                Exception이 발생하지 않는 이상 여러번(5번까지 실행)을 실행해도 정상적으로 작동한다
                transform과 달리 member변수를 선언 / 호출하는 부분을 수정해도 별 문제없이 작동
                
                다만 exception이 바로 외부에 throw되면 더이상 실행이 되지 않는것으로 보아 예외처리를 철저히 해야 할 필요가 있다
             */
            Path targetPath = Path.of("c:\\Dev\\intellij_workspace\\hotload-master\\src\\main\\java\\com\\controller\\", "AsmController.java");
            try {
                CustomCompiler.compile(targetPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            logger.info("ProcessFileEventThread compile complete");

            logger.info("ProcessFileEventThread redefine class start...");
            Path classFilePath = Path.of("c:\\Dev\\intellij_workspace\\hotload-master\\src\\main\\java\\com\\controller\\", "AsmController.class");
            try {
                byte[] classByteCode = Files.readAllBytes(classFilePath);
                Class targetClass = Class.forName("com.controller.AsmController");
                logger.info("target Class : {}",targetClass);
                ClassDefinition cd = new ClassDefinition(targetClass, classByteCode);
                inst.redefineClasses(cd);

                Files.delete(classFilePath);
            } catch (IOException | ClassNotFoundException | UnmodifiableClassException e) {
                e.printStackTrace();
            }
            logger.info("ProcessFileEventThread redefine class complete");
        },20000L, 20000L, TimeUnit.MILLISECONDS);
    }
}
