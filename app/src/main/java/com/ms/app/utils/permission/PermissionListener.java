package com.ms.app.utils.permission;
import java.util.List;

public interface PermissionListener {
  void onGranted();
  void onDenied(List<String> var1);
  void onShouldShowRationale(List<String> var1);
}
