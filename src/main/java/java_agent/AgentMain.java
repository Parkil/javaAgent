package java_agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transformer.AtmTransformer;

import java.lang.instrument.Instrumentation;

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
        inst.addTransformer(new AtmTransformer());
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("agentmain called");
    }

}