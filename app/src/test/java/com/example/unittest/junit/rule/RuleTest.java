package com.example.unittest.junit.rule;

import com.example.unittest.data.Api;
import com.example.unittest.util.FileUtil;
import java.io.File;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.rules.Verifier;
import org.junit.runner.Description;

/**
 * 测试规则演示
 *
 * @author shouxianli on 2020/7/16.
 */
public class RuleTest {

    ///////////////获取测试方法名称
    @Rule
    public TestName testName = new TestName();

    @Test
    public void testName() {
        System.out.println(testName.getMethodName());
    }

    //////////////生成临时文件或文件夹
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testFileCreateAndWrite() throws Exception {
        File file = tempFolder.newFile("simple.txt");
        System.out.println(file.getPath());
        FileUtil.writeStringToFile(file, "Junit Rules!");
        String line = FileUtil.readFileToString(file);
        Assert.assertThat(line, Matchers.is("Junit Rules!"));
    }

    ///////////////收集多个错误信息，最后一同打印
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testMoreCollector() throws Exception {
        String s = null;
        collector.checkThat("Value should not be null", null, Matchers.is(s));

        s = "";
        collector.checkThat("Value should have the length of 1", s.length(), Matchers.is(1));

        s = "Junit!";
        collector.checkThat("Value should have the length of 10", s.length(), Matchers.is(10));
    }

    ///////////////设置超时时间，对所有测试方法起作用
    @Rule
    public Timeout timeout = Timeout.seconds(500);

    @Ignore
    @Test
    public void testTimeout() throws Exception {
        Thread.sleep(300);
    }

    ///////////////测试方法执行之后，做一些校验，对所有测试方法起作用。可以配合TestName对测试方法做区分
    private String sequence;

    @Rule
    public Verifier verifier = new Verifier() {
        @Override
        protected void verify() {
            if ("verifierRunsAfterTest".equals(testName.getMethodName())) {
                //只测试verifierRunsAfterTest方法
                Assert.assertThat(sequence, Matchers.is("do it"));
            }
        }
    };

    @Ignore
    @Test
    public void verifierRunsAfterTest() {
        sequence = "do it";
    }

    ///////////////监测测试过程，对所有测试方法起作用。
    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.println(description.getDisplayName() + " Started");
        }

        @Override
        protected void succeeded(Description description) {
            System.out.println(description.getDisplayName() + " Succeed");
        }

        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println(description.getDisplayName() + " Fail");
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            System.out.println(description.getDisplayName() + " Skipped");
        }

        @Override
        protected void finished(Description description) {
//            System.out.println(description.getDisplayName() + " finished");
        }
    };

    @Test
    public void testTestWatcher() {
        System.out.println("Test invoked");
    }

    ///////////////异常测试
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testAdminLogin2() {
        Api api = new Api();
        //期待抛出ACLException
        expectedException.expect(IllegalArgumentException.class);
        //期待抛出的异常信息中包含"Access Denied"字符串
        expectedException.expectMessage(CoreMatchers.containsString("用户名错误"));
        //当然也可以直接传入字符串，表示期待的异常信息（完全匹配）
//        expectedException.expectMessage("密码错误");
        api.exception2(1);
    }

    ///////////////自定义循环测试
//    @Rule
//    public LoopRule loopRule = new LoopRule(3);
//
//    @Test
//    public void testLoop() {
//        System.out.println("hello");
//    }
}
