package com.example.jinhui.ofomenuview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学习下新版ofo的个人页面的动画？！
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.convex_btn)
    Button convexBtn;
    @BindView(R.id.concave_btn)
    Button concaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.convex_btn, R.id.concave_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.convex_btn:
                //凹进去
                startActivity(new Intent(this, OfoConvcaveMenuActivity.class));
                break;
            case R.id.concave_btn:
                startActivity(new Intent(this, OfoMenuActivity.class));
                break;
        }
    }
}
