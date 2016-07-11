package com.example.demo.bauer.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.bauer.PWDialog;

/**
 * Created by bauer_bao on 16/7/11.
 */
public class DialogTest extends AppCompatActivity implements View.OnClickListener, PWDialog.OnCustomerViewCallBack, PWDialog.OnPWDialogClickListener{
    private Button button1, button2, button3;
    private PWDialog pwDialog;
    private PWDialog pwDialog2;
    private PWDialog pwDialog3;

    private EditText editText;

    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        button1 = (Button) findViewById(R.id.default_dialog1);
        button2 = (Button) findViewById(R.id.default_dialog2);
        button3 = (Button) findViewById(R.id.diy_dialog3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.default_dialog1:
                if (pwDialog == null) {
                    pwDialog = new PWDialog(DialogTest.this, 101)
                            .setPWDialogTitle("dialog1")
                            .setPWDialogTitleColor(R.color.color_9)
                            .setPWDialogMessage("显示取消按钮")
                            .setPWDialogContentGravity(true)
                            .setOnPWDialogClickListener(this)
                            .setPWDialogPositiveButton("yes")
                            .setPWDialogPositiveButtonColor(R.color.color_5)
                            .pwDialogCreate()
                    ;
                }

                pwDialog.pwDilogShow();
                break;

            case R.id.default_dialog2:
                if (pwDialog2 == null) {
                    pwDialog2 = new PWDialog(DialogTest.this, 102)
                            .setPWDialogTitleColor(R.color.color_9)
                            .setPWDialogMessage("显示title")
                            .setPWDialogContentGravity(true)
                            .setOnPWDialogClickListener(this)
                            .setPWDialogPositiveButton("yes")
                            .setPWDialogNegativeButton("no")
                            .setPWDialogPositiveButtonColor(R.color.color_5)
                            .pwDialogCreate()
                    ;
                }

                pwDialog2.pwDilogShow();
                break;

            case R.id.diy_dialog3:
                if (pwDialog3 == null) {
                    pwDialog3 = new PWDialog(DialogTest.this, 103)
                            .setPWDialogTitle("customser dialog")
                            .setPWDialogTitleColor(R.color.color_0)
                            .setPWDialogContentView(R.layout.dialog_edittext, this)
                            .setPWDialogPositiveButton("重置输入框，修改title，并且显示取消按钮")
                            .setOnPWDialogClickListener(this)
                            .pwDialogCreate();
                }
                pwDialog3.pwDilogShow();
                break;

            default:
                break;
        }
    }

    @Override
    public void onNegativeDialogButtonClicked(int dialogId) {
        if (dialogId == 101) {
            if (pwDialog2 != null) {
                pwDialog2.pwDilogShow();
            }
        }
    }

    @Override
    public void onPositiveDialogButtonClicked(int dialogId) {
        if (dialogId == 101) {
            pwDialog.setPWDialogTitle("dialog1 new title");
            pwDialog.setPWDialogMessage("message new" + i);
            if (pwDialog2 != null) {
                pwDialog.setPWDialogNegativeButton("显示对话框2");
            } else {
                pwDialog.setPWDialogNegativeButton("取消");

            }
            pwDialog.pwDilogShow();
            i++;
        } else if (dialogId == 102) {
            pwDialog2.setPWDialogTitle("dialog2 new " + i);
            pwDialog2.pwDilogShow();
            i++;
        } else if (dialogId == 103) {
            editText.setText("103103103103103103");
            pwDialog3.setPWDialogTitle("customer dialog title has changed");
            pwDialog3.setPWDialogNegativeButton("nonoooooo");
            pwDialog3.pwDilogShow();
        }
    }

    @Override
    public void initCustomerView(View view, int dialogId) {
        if (dialogId == 103) {
            editText = (EditText) view.findViewById(R.id.et_text);
            editText.setText("deafult text");
            Button button = (Button) view.findViewById(R.id.button);
            button.setText("点击获取输入框文字");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DialogTest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
