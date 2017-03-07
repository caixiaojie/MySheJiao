package com.flyingfish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flyingfish.R;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.util.Constant;
import com.flyingfish.view.ScannImageView;
import com.flyingfish.zxing.ScanListener;
import com.flyingfish.zxing.ScanManager;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class Scann_Activity extends FragmentActivity implements ScanListener {
    @BindView(R.id.capture_scan_line)
    ImageView scanLine;
    @BindView(R.id.scan_image)
    ScannImageView scanImage;
    @BindView(R.id.capture_crop_view)
    RelativeLayout scanCropView;
    @BindView(R.id.capture_container)
    RelativeLayout scanContainer;
    @BindView(R.id.searchNameEdit)
    EditText searchNameEdit;
    @BindView(R.id.searchPhoneEdit)
    EditText searchPhoneEdit;
    @BindView(R.id.capture_preview)
    SurfaceView scanPreview;
    ScanManager scanManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scann_activity);
        ButterKnife.bind(this);
        FlyingFishIntance.getInstance().addActivity(this);

        scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine, Constant.REQUEST_SCAN_MODE_ALL_MODE, this);
    }


    @OnClick(R.id.queryBtn)
    public void onClick() {
        if (searchNameEdit.getText().toString().equals("") || searchPhoneEdit.getText().toString().equals("")) {
            String input_name_phone = getResources().getString(R.string.input_name_phone);
            SuperCustomToast.getInstance(getApplicationContext()).show(input_name_phone);
            return;
        }
        Intent intent = new Intent(this, Serach_Result_Activity.class);
        intent.putExtra("name", searchNameEdit.getText().toString());
        intent.putExtra("phone", searchPhoneEdit.getText().toString());
        startActivity(intent);
    }

    @Override
    public void scanResult(Result rawResult, Bundle bundle) {
        Intent intent = new Intent(this, Serach_Result_Activity.class);
        intent.putExtra("user_id", rawResult.getText().toString());
                 startActivity(intent);

    }

    @Override
    public void scanError(Exception e) {

    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.ADD_FRIEND_OK){
            finish();
        }
    }
}
