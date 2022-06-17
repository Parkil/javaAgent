package compiler;

import javax.tools.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomCompiler {

    private CustomCompiler(){}
    
    public static void compile(Path javaFilePath) throws IOException {
        List<Path> compilePathList = new ArrayList<>();
        compilePathList.add(javaFilePath);

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromPaths(compilePathList);
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        diagnostics.getDiagnostics().forEach(row ->
            System.out.format("Error on line %d in %s : %s %n", row.getLineNumber(), row.getSource().toUri(), row.getMessage(Locale.ENGLISH))
        );

        for (JavaFileObject compilationUnit : compilationUnits) {
            System.out.println(compilationUnit.getKind().name());
        }

        fileManager.close();
    }
}