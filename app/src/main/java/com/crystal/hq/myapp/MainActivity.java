package com.crystal.hq.myapp;
/**
 * Created by 102003449 on 2017/4/19.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/*弹出对话框使用*/
import android.app.AlertDialog;
import android.content.DialogInterface;

/*使用Gallery需引用以下模块*/
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Gallery;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    //        private TextView mTextView;
    private TextView mTextView01;
    private TextView mTextView02;

    RelativeLayout rl_01;
    LinearLayout ll_01;
    Button mBtn01;
    Button mBtn02;
    Button mBtn03;
    Button mBtn04;
    ImageButton ibtn;

    Button dialogBtn;
    Button progressBtn;
    ProgressDialog myProgressDialog;

    private RadioButton rbtnSexMale;
    private RadioButton rbtnSexFemale;
    private EditText etHigh;
    private Button btnCalc;

    private SlidingDrawer sliding_drawer;
    private GridView gv_Adapter;
    private ImageView im_iv;
    private int[] icons = {R.mipmap.ic_down, R.mipmap.ic_up, R.mipmap.ic_next, R.mipmap.ic_previous, R.mipmap.ic_left, R.mipmap.ic_right};
    private String[] items = {"下", "上", "下一个", "前一个", "左一个", "右一个"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_01 = (RelativeLayout) findViewById(R.id.rl_01);
        ll_01 = (LinearLayout) findViewById(R.id.ll_01);
        mBtn01 = (Button) findViewById(R.id.Btn01);
        mBtn02 = (Button) findViewById(R.id.Btn02);
        mBtn03 = (Button) findViewById(R.id.Btn03);
        mBtn04 = (Button) findViewById(R.id.Btn04);
        ibtn = (ImageButton) findViewById(R.id.ibtn);

        dialogBtn = (Button) findViewById(R.id.dialogBtn);
        progressBtn = (Button) findViewById(R.id.progressBtn);

        //输出屏幕尺寸
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        System.out.print(dm.widthPixels + " , " + dm.heightPixels);
        //修改控件样式
//        mTextView = (TextView) findViewById(R.id.mTextView);
//        mTextView.setText("Hello World ~");
        mTextView01 = (TextView) findViewById(R.id.mTextView01);
        mTextView01.setBackgroundResource(R.color.colorAccent);
        mTextView01.setTextColor(Color.DKGRAY);

        mTextView02 = (TextView) findViewById(R.id.mTextView02);
//        CharSequence str = getString(R.string.tv02);
//        String _str = dm.widthPixels + " , " + dm.heightPixels;
//        mTextView02.setText(_str + str);

        //切换Layout
        mBtn01.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMyLayout();
            }
        });

        /**/
        try {
            Bundle bdl = getIntent().getExtras();
            String strRet = bdl.getString("Str_In");
            mTextView01.setText(strRet);
        } catch (Exception e) {
            e.printStackTrace();
            mTextView01.setText("没有找到合适的Bundle");
        }
        mBtn02.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActivity();

                //传递数值给Interactive App
                Bundle inBd = new Bundle();
                inBd.putString("myAPP_Back", "Back from MyAPP");
                Intent intt = new Intent();
                intt.putExtras(inBd);
                setResult(RESULT_OK, intt);

                MainActivity.this.finish();
            }
        });

        //切换Activity
        mBtn04.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
        //通过OnFocusChangeListener响应ImageButton的onFocus事件
        ibtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    ibtn.setImageResource(R.mipmap.ibtn_pause);
                } else {
                    ibtn.setImageResource(R.mipmap.ibtn_normal);
                }
            }
        });
        //通过OnClickListener响应ImageButton的onClick事件
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用系统标准的makeText()产生Toast信息
                Toast.makeText(MainActivity.this, "你的愿望清单已经发送至圣诞老人的信箱", Toast.LENGTH_LONG).show();
                //自定义Toast格式，如ImageView、TextView、AlterDialog等
                Toast toast = new Toast(MainActivity.this);
                TextView tv = new TextView(MainActivity.this);
                tv.setText("提示Toast信息");
                toast.setView(tv);
                toast.show();
            }
        });

        rbtnSexMale = (RadioButton) findViewById(R.id.male);
        rbtnSexFemale = (RadioButton) findViewById(R.id.female);
        etHigh = (EditText) findViewById(R.id.etHigh);
        //利用键盘的输入事件重写onKey()方法，实现TextView的值为EditText实时输入的值
        etHigh.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mTextView02.setText(etHigh.getText());
                return false;
            }
        });
        btnCalc = (Button) findViewById(R.id.calcBtn);
        btnCalc.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double high = 0.0;
                if (!etHigh.getText().toString().trim().equals(""))
                    high = Double.parseDouble(etHigh.getText().toString());

                String sex = "";
                if (rbtnSexMale.isChecked()) {
                    sex = "M";
                } else {
                    sex = "F";
                }

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("High", high);
                bundle.putString("Sex", sex);
                intent.putExtras(bundle);
                //startActivity(intent);
                startActivityForResult(intent, 0);
                //MainActivity.this.finish();
            }
        });

        //如iphone拖动相片特效-Gallery
        ((Gallery) findViewById(R.id.gallery)).setAdapter(new ImageAdapter(this));

        //弹出对话框
        dialogBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle(R.string.DialogTitle)
//                        .setMessage(R.string.DialogContent)
//                        .setPositiveButton(
//                                R.string.DialogButton,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialoginterface, int i) {
//                                        //对话框单击后要运行的事
//                                        MainActivity.this.finish();
//                                    }
//                                }
//                        )
//                        .show();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.DialogTitle)
                        .setItems(R.array.DialogItems,
                                new DialogInterface.OnClickListener() {
                                    //点击弹出的选项
                                    public void onClick(DialogInterface dialog, int whichItem) {
                                        String[] aryItem = getResources().getStringArray(R.array.DialogItems);
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setMessage(aryItem[whichItem])
                                                .setNeutralButton(R.string.DialogButton, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        /*点击确认后要做的事情*/
                                                    }
                                                }).show();
                                    }
                                })
                        .show();
            }
        });

        /*创建新Button，动态产生按钮并最大化*/
//        Button myBtn = new Button(this);
//        this.setContentView(myBtn);

        progressBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProgressDialog = ProgressDialog.show(
                        MainActivity.this,
                        getString(R.string.ProgressDialogTitle),
                        getString(R.string.ProgressDialogContent),
                        true
                );

                new Thread() {
                    public void run() {
                        try {
                            /*为显示效果，暂停3s示范*/
                            sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            /*卸载创建的myProgressDialog*/
                            myProgressDialog.dismiss();
                        }
                    }
                }.start();
            }
        });
        sliding_drawer = (SlidingDrawer) findViewById(R.id.sliding_drawer);
        gv_Adapter = (GridView) findViewById(R.id.sliding_content);
        im_iv = (ImageView) findViewById(R.id.sliding_iv);
        MyGridViewAdapter adapter = new MyGridViewAdapter(MainActivity.this, items, icons);
        gv_Adapter.setAdapter(adapter);
        sliding_drawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                im_iv.setImageResource(R.mipmap.ibtn_pause);
            }
        });
        sliding_drawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                im_iv.setImageResource(R.mipmap.ibtn_normal);
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context myContext;
        private int[] myImageIds = {
                R.mipmap.ic_down,
                R.mipmap.ic_up,
                R.mipmap.ic_exit,
                R.mipmap.ic_house,
                R.mipmap.ic_left,
                R.mipmap.ic_right,
                R.mipmap.ic_next,
                R.mipmap.ic_previous,
                R.mipmap.ic_search
        };

        public ImageAdapter(Context c) {
            this.myContext = c;
        }

        public int getCount() {
            return this.myImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv = new ImageView(this.myContext);
            iv.setImageResource(this.myImageIds[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new Gallery.LayoutParams(120, 120));
            return iv;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();
                String sex = bundle.getString("Sex");
                Double high = bundle.getDouble("High");
                etHigh.setText("" + high);
                if (sex.equals("M")) {
                    rbtnSexMale.setChecked(true);
                } else {
                    rbtnSexFemale.setChecked(true);
                }
                break;
            default:
                break;
        }
    }

    void changeActivity() {
        setTheme(R.style.AppTheme);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MyActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    void jumpToMyLayout() {
//        setTheme(R.style.MyTheme);
//        setContentView(R.layout.mylayout);
        rl_01.setVisibility(View.GONE);
        ll_01.setVisibility(View.VISIBLE);
        mBtn03.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMainLayout();
            }
        });
    }

    void jumpToMainLayout() {
//        setTheme(R.style.AppTheme);
//        setContentView(R.layout.activity_main);
        ll_01.setVisibility(View.GONE);
        rl_01.setVisibility(View.VISIBLE);
        mBtn01.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMyLayout();
            }
        });
    }
}
