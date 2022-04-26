package com.example.appfood.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appfood.R;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    Sharedata sharedata;
    EditText edt_phone,edt_address;
    Button btn_cf_address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.address_bottom_sheet,null,false);
        edt_phone=view.findViewById(R.id.edt_phone);
        btn_cf_address=view.findViewById(R.id.btn_cf_address);
        edt_address=view.findViewById(R.id.edt_address);
        btn_cf_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=edt_phone.getText().toString();
                String  address=edt_address.getText().toString();
                sharedata.senddata(address,phone);
                dismiss();
            }
        });

        return  view;
    }
    public interface Sharedata
    {
        void senddata(String adress,String phone);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedata= (Sharedata) context;
    }
}
