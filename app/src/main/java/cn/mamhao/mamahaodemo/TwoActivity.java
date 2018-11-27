package cn.mamhao.mamahaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.mamhao.mamahaodemo.test.ShowNotificationTestJob;

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityListUtil.getInstence().addActivityToList(this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WorkerManageUtils.oneWork();
                ShowNotificationTestJob.schedulePeriodic();
//                startActivity(new Intent(TwoActivity.this, TwoActivity.class));
            }
        });
    }
}
