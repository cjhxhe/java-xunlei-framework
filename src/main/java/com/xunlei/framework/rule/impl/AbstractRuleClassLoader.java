package com.xunlei.framework.rule.impl;

import com.xunlei.framework.rule.Rule;
import com.xunlei.framework.rule.RuleClassLoader;
import com.xunlei.framework.rule.RuleException;

import java.io.File;

/**
 * 脚本加载器，默认情况下，根据模板文件生成脚本内容
 * 并在写入{@link ScriptWriter#DEFAULT_OUTPUT_PATH}目录，
 * <p>
 * 一旦脚本文件存在之后，便不会重写源文件，直接编译成
 */
public abstract class AbstractRuleClassLoader implements RuleClassLoader {

    /**
     * 将规则加载至内存
     */
    @Override
    public Class<?> loadClass(Rule rule) throws RuleException {
        ScriptWriter sw = new ScriptWriter(rule);
        File sourceFile = sw.createOrRead();
        return loadClass(sourceFile, sw);
    }

    /**
     * 由子类实现具体类的加载过程
     *
     * @param sourceFile 给定了源文件
     * @return
     * @throws RuleException
     */
    public abstract Class<?> loadClass(File sourceFile, ScriptWriter sw) throws RuleException;

}
