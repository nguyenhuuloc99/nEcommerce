package com.example.appfood.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.example.appfood.R;

public class LoadingDialog {
    Context context;
    Dialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }
    public void  LoadingDialog(String title)
    {
        dialog=new Dialog(context);
      dialog.setContentView(R.layout.dialog2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt=dialog.findViewById(R.id.title);
        txt.setText(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }
        dialog.show();

    }
    public void HideDialog()
    {
        dialog.dismiss();
    }
}
