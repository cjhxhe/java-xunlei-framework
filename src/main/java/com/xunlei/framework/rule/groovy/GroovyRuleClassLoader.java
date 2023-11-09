package com.xunlei.framework.rule.groovy;

import com.xunlei.framework.rule.RuleException;
import com.xunlei.framework.rule.impl.AbstractRuleClassLoader;
import com.xunlei.framework.rule.impl.ScriptWriter;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Groovy Rule Class Loader Implementation
 */
public class GroovyRuleClassLoader extends AbstractRuleClassLoader {

    private GroovyClassLoader GCL = null;
    private CompilerConfiguration config = CompilerConfiguration.DEFAULT;

    public GroovyRuleClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        GCL = new GroovyClassLoader(loader, config);
    }

    @Override
    public Class<?> loadClass(File sourceFile, ScriptWriter sw) throws RuleException {
        try {
            // output
            config.setTargetDirectory(sw.getRootDirectory());
            // no cache
            return GCL.parseClass(new GroovyCodeSource(sourceFile), false);
        } catch (CompilationFailedException e) {
            throw new RuleException("Compile the groovy script error", e);
        } catch (IOException e) {
            throw new RuleException(e);
        }
    }
}
