package com.example.unittest.robolectric;

import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.unittest.MainActivity;
import com.example.unittest.R;
import com.example.unittest.RecycleviewActivity;
import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowAlertDialog;

/**
 * 测试类
 *
 * @author shouxianli on 2020/7/20.
 */
public class MainActivityTest extends BaseRobolectricTest {

    @Test
    public void testCal() {
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        MainActivity mainActivity = controller.get();
        controller.create();
        controller.start();
        controller.resume();

        EditText editText = mainActivity.findViewById(R.id.num1);
        editText.setText("2");
        EditText editText2 = mainActivity.findViewById(R.id.num2);
        editText2.setText("5");
        mainActivity.findViewById(R.id.calculate).performClick();
        TextView textView = mainActivity.findViewById(R.id.sum);
        Assert.assertEquals("计算结果：7", textView.getText());
    }

    @Test
    public void testRecyclerView() {
        ActivityController<RecycleviewActivity> controller = Robolectric
                .buildActivity(RecycleviewActivity.class);
        RecycleviewActivity activity = controller.get();
        controller.create();
        controller.start();
        controller.resume();

        RecyclerView recyclerView = activity.findViewById(R.id.recycleview);
        //必须执行测量、布局，否则没有child
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 1000);

        System.out.println(recyclerView.getChildCount());
        recyclerView.findViewHolderForLayoutPosition(0).itemView.performClick();

        /*
        注意此处是android.app.AlertDialog，不是androidx.appcompat.app.AlertDialog‼️
        appcompat组件需要考虑其他方式验证
         */
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowAlertDialog = Shadows.shadowOf(dialog);
        Assert.assertEquals("Item 0", shadowAlertDialog.getTitle());
    }
}