package com.ms.app.utils.permission;

import android.annotation.TargetApi;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PermissionFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private PermissionListener listener;

    public PermissionFragment() {
    }

    public void setListener(PermissionListener listener) {
        this.listener = listener;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @TargetApi(23)
    public void requestPermissions(@NonNull String[] permissions) {
        List<String> requestPermissionList = new ArrayList();
        String[] var3 = permissions;
        int var4 = permissions.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            String permission = var3[var5];
            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != 0) {
                requestPermissionList.add(permission);
            }
        }
        if (requestPermissionList.isEmpty()) {
            this.permissionAllGranted();
        } else {
            this.requestPermissions((String[]) requestPermissionList.toArray(new String[requestPermissionList.size()]), 1);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                List<String> deniedPermissionList = new ArrayList();

                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] != 0) {
                        deniedPermissionList.add(permissions[i]);
                    }
                }
                if (deniedPermissionList.isEmpty()) {
                    this.permissionAllGranted();
                } else {
                    Iterator var8 = deniedPermissionList.iterator();

                    while (var8.hasNext()) {
                        String deniedPermission = (String) var8.next();
                        boolean flag = this.shouldShowRequestPermissionRationale(deniedPermission);
                        if (!flag) {
                            this.permissionShouldShowRationale(deniedPermissionList);
                            return;
                        }
                    }

                    this.permissionHasDenied(deniedPermissionList);
                }
            }
        }
    }

    private void permissionAllGranted() {
        if (this.listener != null) {
            this.listener.onGranted();
        }
    }

    private void permissionHasDenied(List<String> deniedList) {
        if (this.listener != null) {
            this.listener.onDenied(deniedList);
        }
    }

    private void permissionShouldShowRationale(List<String> deniedList) {
        if (this.listener != null) {
            this.listener.onShouldShowRationale(deniedList);
        }
    }
}
