
package com.gao.smsmanager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements OnClickListener {

    private TabHost mTabHost;
    private View mSlideView;// 页签的滑动背景
    private int mBasicWidth = 0; // 一等分的宽度
    private int mStartX = 0;     // 记住上一次移动完成之后的x轴的偏移量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initTabHost();
    }

    private void initTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mSlideView = findViewById(R.id.slide_view);
        final View mConversationLayout = findViewById(R.id.ll_conversation);
        mConversationLayout.setOnClickListener(this);
        findViewById(R.id.ll_folder).setOnClickListener(this);
        findViewById(R.id.ll_group).setOnClickListener(this);

        // 初始化滑动背景的宽和高
        // 获得视图树的观察者对象, 添加一个当全部布局(layout)完成时的监听事件
        mConversationLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    /**
                     * 全局布局完成时回调.
                     */
                    @Override
                    public void onGlobalLayout() {
                        // 移除全局布局的监听事件,因为修改布局后，整个布局又会重新调用，这样会死循环。
                        mConversationLayout.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);

                        // 得到会话布局的参数, 设置给滑动块
                        // 因为view是在RelativeLayout下面的所以要导入 import
                        // android.widget.RelativeLayout.LayoutParams;
                        LayoutParams lp = (LayoutParams) mSlideView.getLayoutParams();
                        lp.width = mConversationLayout.getWidth();
                        lp.height = mConversationLayout.getHeight();
                        lp.leftMargin = mConversationLayout.getLeft();
                        mSlideView.setLayoutParams(lp);

                        mBasicWidth = findViewById(R.id.rl_conversation).getWidth();
                    }
                });

        addTabSpec("conversation", "会话", R.drawable.tab_conversation, new Intent(this,
                ConversationUI.class));
        addTabSpec("folder", "文件夹", R.drawable.tab_folder, new Intent(this, FolderUI.class));
        addTabSpec("group", "群组", R.drawable.tab_group, new Intent(this, GroupUI.class));
    }

    /**
     * 添加一个页签
     * 
     * @param tag 标记
     * @param label 标题
     * @param icon 图标
     * @param intent 指向的activity
     */
    private void addTabSpec(String tag, String label, int icon, Intent intent) {
        TabSpec newTabSpec = mTabHost.newTabSpec(tag);
        // 设置页签的标题和图标
        newTabSpec.setIndicator(label, getResources().getDrawable(icon));
        // 设置页签指向的显示内同activity
        newTabSpec.setContent(intent);
        mTabHost.addTab(newTabSpec);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_conversation: // 切换到会话页签
                if (!"conversation".equals(mTabHost.getCurrentTabTag())) {//防止重复操作
                    mTabHost.setCurrentTabByTag("conversation");
                    startTranslateAnimation(mStartX, 0);
                    mStartX = 0;
                }
                break;
            case R.id.ll_folder: // 切换到文件夹页签
                if (!"folder".equals(mTabHost.getCurrentTabTag())) {
                    mTabHost.setCurrentTabByTag("folder");
                    startTranslateAnimation(mStartX, mBasicWidth);
                    mStartX = mBasicWidth;
                }
                break;
            case R.id.ll_group: // 切换到群组页签
                if (!"group".equals(mTabHost.getCurrentTabTag())) {
                    mTabHost.setCurrentTabByTag("group");
                    startTranslateAnimation(mStartX, mBasicWidth * 2);
                    mStartX = mBasicWidth * 2;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 给滑动块执行唯一动画
     * 
     * @param fromXDelta 开始位移x轴的偏移量
     * @param toXDelta 结束位移x轴的偏移量
     */
    private void startTranslateAnimation(int fromXDelta, int toXDelta) {
        TranslateAnimation ta = new TranslateAnimation(
                fromXDelta, toXDelta, 0, 0);
        ta.setDuration(500);
        ta.setFillAfter(true); // 停留在动画结束的位置上
        mSlideView.startAnimation(ta);
    }

}
