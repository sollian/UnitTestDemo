package com.example.unittest.junit.custom;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * 循环一定的次数
 *
 * @author shouxianli on 2020/7/16.
 */
public class LoopRule implements TestRule {

    private final int count;

    public LoopRule(int count) {
        this.count = count;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                for (int i = 0; i < count; i++) {
                    int time = i + 1;
                    System.out.println("-------第" + time + "次测试开始-------");
                    base.evaluate();
                    System.out.println("-------第" + time + "次测试结束-------");
                }
            }
        };
    }
}
