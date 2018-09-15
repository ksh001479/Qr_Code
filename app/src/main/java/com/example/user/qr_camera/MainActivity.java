package com.example.user.qr_camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.alhazmy13.gota.Gota;
import net.alhazmy13.gota.GotaResponse;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements Gota.OnRequestPermissionsBack, ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        new Gota.Builder(this).withPermissions(Manifest.permission.CAMERA).requestId(1).setListener(this).check();


    }

    @Override
    public void onRequestBack(int requestId, @NonNull GotaResponse gotaResponse) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.resumeCameraPreview(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("결과값");
        builder.setMessage(result.toString());
        builder.setCancelable(true);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.startCamera();

            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.startCamera();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
//        startActivity(intent);
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        // QR코드/바코드를 스캔한 결과 값을 가져옵니다.
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//
//        // 결과값 출력
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.app_name)
//                .setMessage(result.getContents() + " [" + result.getFormatName() + "]")
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
//    }
}

