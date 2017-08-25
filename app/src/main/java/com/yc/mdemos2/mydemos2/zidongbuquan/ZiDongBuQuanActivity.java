package com.yc.mdemos2.mydemos2.zidongbuquan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yc.mdemos2.mydemos2.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.yokomark.widget.account.autocomp.AccountAutoCompleteEditText;

public class ZiDongBuQuanActivity extends AppCompatActivity {

    @Bind(R.id.any)
    AccountAutoCompleteEditText any;
    @Bind(R.id.email)
    AccountAutoCompleteEditText email;
    @Bind(R.id.phone_number)
    AccountAutoCompleteEditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_dong_bu_quan);
        ButterKnife.bind(this);
    }
}
