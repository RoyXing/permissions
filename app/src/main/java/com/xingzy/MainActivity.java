package com.xingzy;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.annotation.NeedsPermission;
import com.annotation.OnNeverAskAgain;
import com.annotation.OnPermissionDenied;
import com.annotation.OnShowRationale;
import com.library.PermissionManager;
import com.library.listener.PermissionRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NeedsPermission
    public void cameraRequestSuccess() {
        Toast.makeText(this, "权限给予成功", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain
    public void onNeverAskAgain() {
        new AlertDialog.Builder(this)
                .setMessage("用户选择不再询问后的提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    }
                })
                .show();
    }

    @OnPermissionDenied
    public void onPermissionDenied() {
        Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_SHORT).show();
        PermissionManager.request(this, new String[]{Manifest.permission.CAMERA});

    }

    @OnShowRationale
    public void onShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("提示用户为何要开启权限")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // 再次执行权限请求
                        request.proceed();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void camera(View view) {
        PermissionManager.request(this, new String[]{Manifest.permission.CAMERA});
    }
}
