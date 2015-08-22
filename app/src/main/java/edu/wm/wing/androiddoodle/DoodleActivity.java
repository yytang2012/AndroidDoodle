package edu.wm.wing.androiddoodle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.wm.wing.androiddoodle.Doodle.ActionType;

public class DoodleActivity extends AppCompatActivity implements View.OnClickListener {

    private Doodle mDoodle;

    private AlertDialog mColorDialog;
    private AlertDialog mPaintDialog;
    private AlertDialog mShapeDialog;

    public static String strShapeTitleEnglish = "Choose a Shape";
    public static String strShapeTitleChinese = "选择形状";
    public static String strShapeTypeEnglish[] = { "Path", "Line", "Rectangle", "Circle",
            "Filled Rectangle", "Filled Circle" };
    public static String strShapeTypeChinese[] = { "路径", "直线", "矩形", "圆形",
            "实心矩形", "实心圆" };

    public static String strSizeTitleEnglish = "Choose a Size";
    public static String strSizeTitleChinese = "选择画笔粗细";
    public static String strSizeTypeEnglish[] = {"Small", "Middle", "Big"};
    public static String strSizeTypeChinese[] = { "细", "中", "粗"};

    public static String strColorTitleEnglish = "Choose a Color";
    public static String strColorTitleChinese = "选择颜色";
    public static String strColorTypeEnglish[] = {"Red", "Yellow", "Blue", "Green", "Light Grey"};
    public static String strColorTypeChinese[] = {"红色", "黄色", "蓝色", "绿色", "亮灰色"};

    public static String saveToDiskEnglish = "Save";
    public static String saveToDiskChinese = "保存";

    public static String curStrShapeTitle = strShapeTitleEnglish;
    public static String curstrShapeType[] = strShapeTypeEnglish;
    public static String curStrSizeTitle = strSizeTitleEnglish;
    public static String curstrSizeType[] = strSizeTypeEnglish;
    public static String curStrColorTitle = strColorTitleEnglish;
    public static String curstrColorType[] = strColorTypeEnglish;
    public static String curSaveToDisk = saveToDiskEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        mDoodle = (Doodle) findViewById(R.id.doodle_surfaceview);
        mDoodle.setSize(dip2px(5));

        findViewById(R.id.color_picker).setOnClickListener(this);
        findViewById(R.id.paint_picker).setOnClickListener(this);
        findViewById(R.id.eraser_picker).setOnClickListener(this);
        findViewById(R.id.shape_picker).setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDoodle.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_picker:
                showColorDialog();
                break;
            case R.id.paint_picker:
                showSizeDialog();
                break;
            case R.id.eraser_picker:
                mDoodle.setType(ActionType.Path);
                mDoodle.setSize(dip2px(20));
                mDoodle.setColor("#ffffff");
                break;
            case R.id.shape_picker:
                showShapeDialog();
                break;

            default:
                break;
        }
    }

    private void showShapeDialog() {
        if (mShapeDialog == null) {
            mShapeDialog = new AlertDialog.Builder(this)
                    .setTitle(curStrShapeTitle)
                    .setSingleChoiceItems(
                            curstrShapeType, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which + 1) {
                                        case 1:
                                            mDoodle.setType(ActionType.Path);
                                            break;
                                        case 2:
                                            mDoodle.setType(ActionType.Line);
                                            break;
                                        case 3:
                                            mDoodle.setType(ActionType.Rect);
                                            break;
                                        case 4:
                                            mDoodle.setType(ActionType.Circle);
                                            break;
                                        case 5:
                                            mDoodle.setType(ActionType.FillecRect);
                                            break;
                                        case 6:
                                            mDoodle.setType(ActionType.FilledCircle);
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mShapeDialog.show();
    }

    private void showSizeDialog() {
        if (mPaintDialog == null) {
            mPaintDialog = new AlertDialog.Builder(this)
                    .setTitle(curStrSizeTitle)
                    .setSingleChoiceItems(curstrSizeType, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            mDoodle.setSize(dip2px(5));
                                            break;
                                        case 1:
                                            mDoodle.setSize(dip2px(10));
                                            break;
                                        case 2:
                                            mDoodle.setSize(dip2px(15));
                                            break;
                                        default:
                                            break;
                                    }

                                    dialog.dismiss();
                                }
                            }).create();
        }
        mPaintDialog.show();
    }

    private void showColorDialog() {
        if (mColorDialog == null) {
            mColorDialog = new AlertDialog.Builder(this)
                    .setTitle(curStrColorTitle)
                    .setSingleChoiceItems(curstrColorType, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            mDoodle.setColor(Color.RED);
                                            break;
                                        case 1:
                                            mDoodle.setColor(Color.YELLOW);
                                            break;
                                        case 2:
                                            mDoodle.setColor(Color.BLUE);
                                            break;
                                        case 3:
                                            mDoodle.setColor(Color.GREEN);
                                            break;
                                        case 4:
                                            mDoodle.setColor(Color.LTGRAY);
                                            break;

                                        default:
                                            break;
                                    }

                                    dialog.dismiss();
                                }
                            }).create();
        }
        mColorDialog.show();
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, curSaveToDisk);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doodle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mDoodle.back()) {
            super.onBackPressed();
        }
    }

    public static void savePicByPNG(Bitmap b, String filePath) {
        FileOutputStream fos = null;
        try {
//			if (!new File(filePath).exists()) {
//				new File(filePath).createNewFile();
//			}
            fos = new FileOutputStream(filePath);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
