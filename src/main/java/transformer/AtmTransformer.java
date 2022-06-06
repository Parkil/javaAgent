package transformer;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AtmTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String instrumentedClassName = "com.controller.AsmController";
        String instrumentedMethodName = "getConfig";
        byte[] bytecode = classfileBuffer;
        try {
            ClassPool cPool = ClassPool.getDefault();
            CtClass ctClass = cPool.makeClass(new ByteArrayInputStream(bytecode));
            CtMethod[] ctClassMethods = ctClass.getDeclaredMethods();
            for (CtMethod ctClassMethod : ctClassMethods) {
                if (ctClassMethod.getDeclaringClass().getName().equals(instrumentedClassName)
                        && ctClassMethod.getName().equals(instrumentedMethodName)) {
                    System.out.println("find target");
                    ctClassMethod.insertBefore("System.out.println(\"[Instrumentation] Entering method\");");
                    ctClassMethod.insertAfter("System.out.println(\"[Instrumentation] Exiting method\");");
                    bytecode = ctClass.toBytecode();
                }
            }
        } catch (IOException e) {
            throw new IllegalClassFormatException(e.getMessage());
        } catch (RuntimeException e) {
            throw new IllegalClassFormatException(e.getMessage());
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
        return bytecode;
    }
}
