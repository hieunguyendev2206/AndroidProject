package vn.poly.quanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1android.R;

import vn.poly.quanlybanhang.Activity.MatHangActivity;

public class manhinhchao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MatHangActivity.class));
            finish();
        },2500);

    }
}