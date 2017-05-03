package com.crystal.hq.myapp;

/**
 * Created by 102003449 on 2017/4/21.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MyActivity extends Activity {
    private Button mBtn01;
    private TextView tvInfo;
    private Button backBtn;

    private TextView spinnerChooseItem;
    private Spinner spinner;
    private static final String[] cityItems = {
            "北京", "上海", "天津", "重庆", "江苏"
    };
    private ArrayAdapter<String> arrAdapter;
    private Animation spinner_anim;

    private EditText inputsearchET;
    private Button searchfileBtn;
    private TextView fileshowTV;
    private ListView listview;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> file_research_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);

        mBtn01 = (Button) findViewById(R.id.Btn01);
        mBtn01.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
        final Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String sex = bundle.getString("Sex");
            Double high = bundle.getDouble("High");
            String sexText = "";
            if (sex.equals("M")) {
                sexText = "男性";
            } else {
                sexText = "女性";
            }
            String weight = getWeight(sex, high);
            tvInfo = (TextView) findViewById(R.id.tvInfo);
            tvInfo.setTypeface(Typeface.createFromAsset(getAssets(), "fronts/HARLOWSI.TTF"));
            tvInfo.setText("你是一位" + sexText + "\n你的身高是" + high + "厘米\n你的标准体重是" + weight + "公斤!");

            backBtn = (Button) findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyActivity.this.setResult(RESULT_OK, intent);
                    MyActivity.this.finish();
                }
            });
        }

        spinnerChooseItem = (TextView) findViewById(R.id.spinnerChoose_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityItems);
        arrAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(arrAdapter);
        spinner_anim = AnimationUtils.loadAnimation(this, R.anim.spinner_anim);

        //将spinner添加OnItemSelectionListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerChooseItem.setText("你所在的城市为：" + cityItems[position]);
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Something
            }
        });

        //添加OnTouch事件
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //设置spinner的动画
                v.startAnimation(spinner_anim);
                v.setVisibility(View.VISIBLE);
                return false;
            }
        });
        spinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Do Something
            }
        });

        inputsearchET = (EditText) findViewById(R.id.input_search_ET);
        searchfileBtn = (Button) findViewById(R.id.search_file_btn);
        fileshowTV = (TextView) findViewById(R.id.file_show_tv);
        listview =(ListView)findViewById(R.id.list_view);
        file_research_result =new ArrayList<>();

        searchfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String keyword = inputsearchET.getText().toString();
                file_research_result.clear();
                if (keyword.equals(""))
                {
                    result = "请输入查找的关键字信息";
                    file_research_result.add(result);
                }
                else {
                    File[] files = new File("/").listFiles();
                    for (File f : files) {
                        if (f.getName().indexOf(keyword) >= 0)
                        {
                            result += f.getPath() + "\n";
                            file_research_result.add(f.getPath());
                        }
                    }
                    if (result.equals(""))
                    {
                        result = "未找到文件";
                        file_research_result.add(result);
                    }
                }
                fileshowTV.setText(result);
                //使用listview显示内容
                listAdapter=new ArrayAdapter<>(MyActivity.this,R.layout.listview_layout, file_research_result);
                listview.setAdapter(listAdapter);
            }
        });
    }

    String getWeight(String sex, Double high) {
        String weight;
        if (sex.equals("M")) {
            weight = String.format("%.2f", ((high - 80) * 0.7));
        } else {
            weight = String.format("%.2f", ((high - 70) * 0.6));
        }
        return weight;
    }

    void changeActivity() {
        setTheme(R.style.Theme);
        Intent intent = new Intent();
        intent.setClass(MyActivity.this, MainActivity.class);
        startActivity(intent);
        MyActivity.this.finish();
    }
}
