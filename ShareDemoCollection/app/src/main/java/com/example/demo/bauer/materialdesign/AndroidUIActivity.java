package com.example.demo.bauer.materialdesign;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.example.demo.R;

import java.util.Random;

import at.markushi.ui.ActionView;
import at.markushi.ui.RevealColorView;
import at.markushi.ui.action.BackAction;
import at.markushi.ui.action.CloseAction;
import at.markushi.ui.action.DrawerAction;
import at.markushi.ui.action.PlusAction;

/**
 * Created by bauer_bao on 16/3/9.
 */
public class AndroidUIActivity extends AppCompatActivity implements View.OnClickListener{
    private ActionView backAction, closeAction, plusAction, drawerAction;
    private int count = 0;
    private RevealColorView revealColorView;

    private MaterialMenuView materialMenu;

    private int menuState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bauer_activity_android_ui);
        backAction = (ActionView) findViewById(R.id.back_action);
        closeAction = (ActionView) findViewById(R.id.close_action);
        plusAction = (ActionView) findViewById(R.id.plus_action);
        drawerAction = (ActionView) findViewById(R.id.drawer_action);
        revealColorView = (RevealColorView) findViewById(R.id.reveal);

        backAction.setOnClickListener(this);
        closeAction.setOnClickListener(this);
        plusAction.setOnClickListener(this);
        drawerAction.setOnClickListener(this);
        drawerAction.setColor(getResources().getColor(R.color.color_0));

        materialMenu = (MaterialMenuView) findViewById(R.id.material_menu_view);
        materialMenu.setColor(getResources().getColor(R.color.color_0));
        materialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    menuState = generateState(menuState);
                    materialMenu.animateState(intToState(menuState));
            }
        });


    }

    @Override
    public void onClick(View v) {
        int color = Color.RED;
        switch (count % 5) {
            case 0:
                backAction.setAction(new CloseAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                color = Color.BLACK;
                break;

            case 1:
                backAction.setAction(new PlusAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                color = Color.BLUE;
                break;

            case 2:
                backAction.setAction(new DrawerAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                color = Color.YELLOW;
                break;

            case 3:
                backAction.setAction(new BackAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                color = Color.GREEN;
                break;

            case 4:
                backAction.setAction(new PlusAction(), new CloseAction(), ActionView.ROTATE_COUNTER_CLOCKWISE, 1000);
                color = Color.GRAY;
                break;
        }
        count++;
        revealColorView.reveal(100, 100, color, 0, 600, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                revealColorView.hide(100, 100, Color.CYAN, 0, 600, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
}
