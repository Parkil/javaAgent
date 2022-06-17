package java_agent;

import compiler.CustomCompiler;
import java_agent.test_thread.TestPollingThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AgentMain {
    private static final Logger logger = LoggerFactory.getLogger(AgentMain.class);

    private AgentMain() {}

    public static void premain(String agentArgs, Instrumentation inst) {
        logger.info("premain called");

        /*
        Path testPath = Path.of("src", "main", "java", "com", "example", "demo", "controller", "TestController.java");
        logger.info("test path exists :{}", Files.exists(testPath));

        try {
            logger.info("file con test : {}",Files.readAllLines(testPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */
//        new TestPollingThread().processEventThread(inst);
//        transformTest(inst);

        /*
        //redefineClasses를 사용하는건 맞는듯한데 source변경사항을 완전히 cover하기 위해서 재 compile을 쓰려고 하다보니 spring 같이 여러 lib가 포함되어 있는 파일은 compile이 되지 않는 문제가 있음
        // -> 다시 확인해보니 premain에서는 피 호출 app의 내용을 가져올수 있기 때문에 정상적으로 compile이 되지만 따로 main을 써서 하려고 하면 문제가 발생할듯.
        Path test = Path.of("d:/","AsmController.class");
        try {
            byte[] classByteCode = Files.readAllBytes(test);
            Class targetClass = Class.forName("com.controller.AsmController");
            logger.info("target Class : {}",targetClass);
            ClassDefinition cd = new ClassDefinition(targetClass, classByteCode);
            inst.redefineClasses(cd);
        } catch (IOException | ClassNotFoundException | UnmodifiableClassException e) {
            e.printStackTrace();
        }*/

        new TestPollingThread().processEventThread(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("agentmain called");
    }
}