package com.example.demo.bauer.materialdesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.example.demo.R;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.LayoutRipple;
import com.gc.materialdesign.widgets.ColorSelector;
import com.gc.materialdesign.widgets.Dialog;
import com.nineoldandroids.view.ViewHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by bauer_bao on 16/3/9.
 */
public class MaterialDesignLibActivity extends Activity implements ColorSelector.OnColorSelectedListener
, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    int backgroundColor = Color.parseColor("#1E88E5");
    ButtonFloatSmall buttonSelectColor;

    private MaterialMenuView materialMenu;

    private int menuState;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bauer_activity_material_design_lib);

        materialMenu = (MaterialMenuView) findViewById(R.id.material_menu_view);
        materialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialMenu.getState() == MaterialMenuDrawable.IconState.ARROW) {
                    finish();
                } else {
                    menuState = generateState(menuState);
                    materialMenu.animateState(intToState(menuState));

                }
            }
        });

        buttonSelectColor = (ButtonFloatSmall) findViewById(R.id.buttonColorSelector);
        buttonSelectColor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ColorSelector colorSelector = new ColorSelector(MaterialDesignLibActivity.this, backgroundColor, MaterialDesignLibActivity.this);
                colorSelector.show();
            }
        });

        LayoutRipple layoutRipple = (LayoutRipple) findViewById(R.id.itemButtons);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignLibActivity.this,ButtonsActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemSwitches);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignLibActivity.this,SwitchActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemProgress);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaterialDesignLibActivity.this,ProgressActivity.class);
                intent.putExtra("BACKGROUND", backgroundColor);
                startActivity(intent);
            }
        });
        layoutRipple = (LayoutRipple) findViewById(R.id.itemWidgets);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Intent intent = new Intent(MaterialDesignLibActivity.this,WidgetActivity.class);
//                intent.putExtra("BACKGROUND", backgroundColor);
//                startActivity(intent);
                Dialog dialog = new Dialog(MaterialDesignLibActivity.this, "Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MaterialDesignLibActivity.this, "Click accept button", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setOnCancelButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MaterialDesignLibActivity.this, "Click cancel button", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

        layoutRipple = (LayoutRipple) findViewById(R.id.time_picker);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        MaterialDesignLibActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                tpd.setThemeDark(true);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        layoutRipple = (LayoutRipple) findViewById(R.id.date_picker);


        setOriginRiple(layoutRipple);

        layoutRipple.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MaterialDesignLibActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    private void setOriginRiple(final LayoutRipple layoutRipple){

        layoutRipple.post(new Runnable() {

            @Override
            public void run() {
                View v = layoutRipple.getChildAt(0);
                layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth() / 2);
                layoutRipple.setyRippleOrigin(ViewHelper.getY(v) + v.getHeight() / 2);

                layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));

                layoutRipple.setRippleSpeed(30);
            }
        });

    }

    @Override
    public void onColorSelected(int color) {
        backgroundColor = color;
        buttonSelectColor.setBackgroundColor(color);
    }


    private int generateState(int previous) {
        int generated = new Random().nextInt(4);
        return generated != previous ? generated : generateState(previous);
    }

    private MaterialMenuDrawable.IconState intToState(int state) {
        switch (state) {
            case 0:
                return MaterialMenuDrawable.IconState.BURGER;
            case 1:
                return MaterialMenuDrawable.IconState.ARROW;
            case 2:
                return MaterialMenuDrawable.IconState.X;
            case 3:
                return MaterialMenuDrawable.IconState.CHECK;
        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1, int i2) {

    }
}
