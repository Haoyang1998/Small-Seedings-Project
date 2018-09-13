package com.seuchild.smallseedling.calendar;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;


import com.seuchild.smallseedling.R;

public class CalendarDialog extends Dialog implements
        NumberPicker.OnValueChangeListener, NumberPicker.Formatter, NumberPicker.OnScrollListener {

        /**
         * 上下文对象 *
         */
        Activity context;

        private NumberPicker np_year,np_month,np_day;

        private Button btn_save,btn_cancel;

        private int year,month,day;
        public TextInputEditText text_content;

//        public String notice;

        private View.OnClickListener mClickListener;

        public CalendarDialog(Activity context) {
            super(context);
            this.context = context;
        }

        public CalendarDialog(Activity context, int theme, View.OnClickListener mClickListener,int y,int m,int d) {
            super(context, theme);
            this.context = context;
            this.mClickListener = mClickListener;
            setValue(y,m,d);
        }

    private void setValue(int y, int m, int d) {
            year = y;
            month = m;
            day = d;
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 指定布局
            this.setContentView(R.layout.activity_calendar_dialog);

            np_year = findViewById(R.id.numer_pick_year);
            np_month = findViewById(R.id.numer_pick_month);
            np_day = findViewById(R.id.numer_pick_day);
            initNP();


            text_content = findViewById(R.id.dialog_tiet);

            /*
             * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
             * 对象,这样这可以以同样的方式改变这个Activity的属性.
             */
            Window dialogWindow = this.getWindow();

            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogWindow.setAttributes(p);

            // 根据id在布局中找到控件对象
            btn_save = (Button) findViewById(R.id.dialog_button);
            btn_cancel = (Button) findViewById(R.id.dialog_button_cancel);

            // 为按钮绑定点击事件监听器
            btn_save.setOnClickListener(this.mClickListener);

            btn_cancel.setOnClickListener(this.mClickListener);

            this.setCancelable(false);
        }

    private void initNP() {
        np_year.setFormatter(this);
        np_year.setOnValueChangedListener(this);
        np_year.setOnScrollListener(this);
        np_year.setMaxValue(2100);
        np_year.setMinValue(1900);
        np_year.setValue(year);

        np_month.setFormatter(this);
        np_month.setOnValueChangedListener(this);
        np_month.setOnScrollListener(this);
        np_month.setMaxValue(12);
        np_month.setMinValue(1);
        np_month.setValue(month);



        np_day.setFormatter(this);
        np_day.setOnValueChangedListener(this);
        np_day.setOnScrollListener(this);
        np_day.setMaxValue(checkMonth(np_month));
        np_day.setMinValue(1);
        np_day.setValue(day);

    }

    public int getyear(){
            return np_year.getValue();
    }
    public int getmon(){
        return np_month.getValue();
    }
    public int getday(){
        return np_day.getValue();
    }
    private int checkMonth(NumberPicker np){
        int m = np_month.getValue();
        if (m == 1 || m == 3 ||m == 5 ||m == 7 ||m == 8 ||m == 10 ||m == 12 )
            return 31;
        else if(m == 2) {
            if(isLeapYear(np_year))
                return 29;
            else
                return 28;
        }
        else
            return 30;
    }

    private boolean isLeapYear(NumberPicker np){
            int d = np.getValue();
            if(d%4 == 0){
                if((d%100 != 0 )||( d%400 == 0)){
                    return true;
                }else
                    return false;
            }
            else
                return false;
    }


    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
