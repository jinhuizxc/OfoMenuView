package com.example.jinhui.ofomenuview;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.jinhui.ofomenuview.drawable.MenuBrawable;
import com.example.jinhui.ofomenuview.view.OfoContentLayout;
import com.example.jinhui.ofomenuview.view.OfoMenuLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/26.
 * Email:1004260403@qq.com
 *
 * 学习自：https://github.com/xiangcman/OfoMenuView-master
 */

public class OfoMenuActivity extends AppCompatActivity {

    @BindView(R.id.start_ofo)
    Button startOfo;
    @BindView(R.id.close)
    ImageView close;
    // contennt中列表view，主要是控制自己的动画
    @BindView(R.id.ofo_content)
    OfoContentLayout ofoContent;
    @BindView(R.id.menu_content)
    FrameLayout menuContent;
    // 最外层的view，用来管理title和content的动画
    @BindView(R.id.ofo_menu)
    OfoMenuLayout ofoMenu;

    int count;
    MenuBrawable menuBrawable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofo_layout);
        ButterKnife.bind(this);

        Window window = getWindow();
        // API的判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

         menuBrawable = new MenuBrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.default_avatar_img), OfoMenuActivity.this, menuContent, getType());
        //        final MenuBrawable menuBrawable = new MenuBrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.bitmap), OfoMenuActivity.this, menu);
        menuContent.setBackground(menuBrawable);

        // setOnBitmapClickListener()监听
        menuBrawable.setOnBitmapClickListener(new MenuBrawable.OnBitmapClickListener() {
            @Override
            public void bitmapClick() {
                count++;
                if (count % 2 == 0) {
                    menuBrawable.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.single));
                } else {
                    menuBrawable.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.timg));
                }
            }
        });


//        startOfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                menuBrawable.setRadian(MenuBrawable.CONCAVE);
////                startConvexBtn.setVisibility(View.GONE);
////                startConcaveBtn.setVisibility(View.GONE);
//                ofoMenu.setVisibility(View.VISIBLE);
//                ofoMenu.open();
//            }
//        });

        //关闭menu
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ofoMenu.close();
            }
        });

        //menu的监听
        ofoMenu.setOfoMenuStatusListener(new OfoMenuLayout.OfoMenuStatusListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
                startOfo.setVisibility(View.VISIBLE);
//                startConcaveBtn.setVisibility(View.VISIBLE);
            }
        });

        //给menu设置content部分
        ofoMenu.setOfoContentLayout(ofoContent);

    }

    @Override
    public void onBackPressed() {
        if (ofoMenu.isOpen()) {
            ofoMenu.close();
            return;
        }
        super.onBackPressed();
    }

    protected int getType() {
        return MenuBrawable.CONVEX;
    }


    @OnClick({R.id.start_ofo, R.id.ofo_content, R.id.menu_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_ofo:
                startMenu();
                break;
            case R.id.ofo_content:
                break;
            case R.id.menu_content:
                break;
        }
    }

    /**
     *  启动menu
     */
    private void startMenu() {
//        menuBrawable.setRadian(MenuBrawable.CONVEX);
        startOfo.setVisibility(View.GONE);
//                startConcaveBtn.setVisibility(View.GONE);
        ofoMenu.setVisibility(View.VISIBLE);
        ofoMenu.open();
    }
}
