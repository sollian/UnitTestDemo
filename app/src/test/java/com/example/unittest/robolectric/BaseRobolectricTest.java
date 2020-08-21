package com.example.unittest.robolectric;

import android.app.Application;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.example.unittest.MyApplication;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/17.
 */
@RunWith(RobolectricTestRunner.class)
//该数组中的类自动替代原始对象
@Config(manifest = "AndroidManifest.xml", sdk = 28, shadows = ShadowLog.class, application = MyApplication.class)
//若测试失败，先检查是不是有类没有加进来
@PowerMockIgnore({
        "org.mockito.*",
        "org.robolectric.*",
        "android.*",
        "androidx.*",
        "org.json.*",
        "sun.security.*",
        "javax.net.*",
        //注意一定要添加自定义的application类
        "com.example.unittest.MyApplication",
})
public abstract class BaseRobolectricTest {

    /**
     * 这个rule一定要有，解决powermock和robolectric兼容性
     */
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @BeforeClass
    public static void setUp() {
        //Log.e会调用System.out输出
        ShadowLog.stream = System.out;
        initRxJava();
    }

    public Application getApplication() {
        return ApplicationProvider.getApplicationContext();
    }

    public Context getContext() {
        return getApplication();
    }

    /**
     * rxjava全部切换到当前线程执行
     */
    private static void initRxJava() {
        RxJavaPlugins.setInitComputationSchedulerHandler(
                new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                        return Schedulers.trampoline();
                    }
                });
        RxJavaPlugins.setInitIoSchedulerHandler(
                new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                        return Schedulers.trampoline();
                    }
                });
        RxJavaPlugins.setInitNewThreadSchedulerHandler(
                new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                        return Schedulers.trampoline();
                    }
                });
        RxJavaPlugins.setInitSingleSchedulerHandler(
                new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                        return Schedulers.trampoline();
                    }
                });
    }
}
