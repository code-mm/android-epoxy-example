package com.ms.app.utils.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtilsImpl {

    private PermissionUtilsImpl() {
    }


    //group:android.permission-group.CONTACTS
//    permission:android.permission.WRITE_CONTACTS
//    permission:android.permission.GET_ACCOUNTS
//    permission:android.permission.READ_CONTACTS
//
//    group:android.permission-group.PHONE
//    permission:android.permission.READ_CALL_LOG
//    permission:android.permission.READ_PHONE_STATE
//    permission:android.permission.CALL_PHONE
//    permission:android.permission.WRITE_CALL_LOG
//    permission:android.permission.USE_SIP
//    permission:android.permission.PROCESS_OUTGOING_CALLS
//    permission:com.android.voicemail.permission.ADD_VOICEMAIL
//
//    group:android.permission-group.CALENDAR
//    permission:android.permission.READ_CALENDAR
//    permission:android.permission.WRITE_CALENDAR
//
//    group:android.permission-group.CAMERA
//    permission:android.permission.CAMERA
//
//    group:android.permission-group.SENSORS
//    permission:android.permission.BODY_SENSORS
//
//    group:android.permission-group.LOCATION
//    permission:android.permission.ACCESS_FINE_LOCATION
//    permission:android.permission.ACCESS_COARSE_LOCATION
//
//    group:android.permission-group.STORAGE
//    permission:android.permission.READ_EXTERNAL_STORAGE
//    permission:android.permission.WRITE_EXTERNAL_STORAGE
//
//    group:android.permission-group.MICROPHONE
//    permission:android.permission.RECORD_AUDIO
//
//    group:android.permission-group.SMS
//    permission:android.permission.READ_SMS
//    permission:android.permission.RECEIVE_WAP_PUSH
//    permission:android.permission.RECEIVE_MMS
//    permission:android.permission.RECEIVE_SMS
//    permission:android.permission.SEND_SMS
//    permission:android.permission.READ_CELL_BROADCASTS


    //    group:android.permission-group.CONTACTS
//    permission:android.permission.WRITE_CONTACTS 联系人
//    permission:android.permission.GET_ACCOUNTS 联系人
//    permission:android.permission.READ_CONTACTS 联系人
//
//    group:android.permission-group.PHONE
//    permission:android.permission.READ_CALL_LOG 手机
//    permission:android.permission.READ_PHONE_STATE 手机
//    permission:android.permission.CALL_PHONE 手机
//    permission:android.permission.WRITE_CALL_LOG 手机
//    permission:android.permission.USE_SIP 手机
//    permission:android.permission.PROCESS_OUTGOING_CALLS 手机
//    permission:com.android.voicemail.permission.ADD_VOICEMAIL 手机
//
//    group:android.permission-group.CALENDAR
//    permission:android.permission.READ_CALENDAR 日历
//    permission:android.permission.WRITE_CALENDAR 日历
//
//    group:android.permission-group.CAMERA
//    permission:android.permission.CAMERA 相机
//
//    group:android.permission-group.SENSORS
//    permission:android.permission.BODY_SENSORS 传感器
//
//    group:android.permission-group.LOCATION
//    permission:android.permission.ACCESS_FINE_LOCATION 位置
//    permission:android.permission.ACCESS_COARSE_LOCATION 位置
//
//    group:android.permission-group.STORAGE
//    permission:android.permission.READ_EXTERNAL_STORAGE 存储
//    permission:android.permission.WRITE_EXTERNAL_STORAGE 存储
//
//    group:android.permission-group.MICROPHONE
//    permission:android.permission.RECORD_AUDIO 麦克风
//
//    group:android.permission-group.SMS
//    permission:android.permission.READ_SMS 短信
//    permission:android.permission.RECEIVE_WAP_PUSH 短信
//    permission:android.permission.RECEIVE_MMS 短信
//    permission:android.permission.RECEIVE_SMS 短信
//    permission:android.permission.SEND_SMS 短信
//    permission:android.permission.READ_CELL_BROADCASTS 短信


    private static final String TAG = "PermissionUtils";

    private static PermissionUtils permissionUtil;

    private static List<String> PERMISSIONS = new ArrayList<>();

    private static List<String> PERMISSIONS_RUNTIME = new ArrayList<>();

    private static String[] ps;

    private static FragmentActivity mActivity;

    private static String ok = "确定";
    private static String exit = "退出";
    private static String hintMsg = "";

    public static interface CallBack {
        void callBack();
    }

    private static CallBack mCallBack;


    private static List<String> agree = new ArrayList<>();


    private static List<String> cpPer = new ArrayList<>();

    public static void requestPermission(FragmentActivity activity, CallBack callBack, String... pers) {

        if (activity == null) {
            throw new NullPointerException("activity null");
        }

        if (callBack == null) {
            throw new NullPointerException("callBack null");
        }

        mActivity = activity;

        mCallBack = callBack;

        cpPer.clear();

        for (String it : pers) {

            cpPer.add(it);

        }

        reqper(activity);
    }


    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private static void reqper(FragmentActivity activity) {


        // 判断系统版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.getApplicationInfo().targetSdkVersion > 22) {

            permissionUtil = new PermissionUtils(activity);
            PERMISSIONS.clear();
            PERMISSIONS_RUNTIME.clear();
            agree.clear();


            // ysdk 所需的动态权限

//            PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE);
//            PERMISSIONS.add(Manifest.permission.READ_SMS);
//            PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);

            if (cpPer.size() > 0) {
                for (String it : cpPer) {
                    PERMISSIONS.add(it);
                }
            }

            // 删除重复的权限
            removeDuplicate(PERMISSIONS);

            for (String it : PERMISSIONS) {
                int per = ContextCompat.checkSelfPermission(mActivity, it);
                if (per == PackageManager.PERMISSION_DENIED) {

                    Log.e(TAG, " reqper:  " + it + "  NO ");

                    PERMISSIONS_RUNTIME.add(it);
                }
            }

            Log.e(TAG, "reqper: " + PERMISSIONS_RUNTIME.toString());

            ps = new String[PERMISSIONS_RUNTIME.size()];

            int i = 0;

            for (String it : PERMISSIONS_RUNTIME) {
                ps[i++] = it;
            }

            if (ps.length > 0) {
                requestPermission();
            } else {
                if (mCallBack != null) {
                    mCallBack.callBack();
                }
            }
        } else {
            if (mCallBack != null) {
                mCallBack.callBack();
            }
        }
    }


    private static void requestPermission(String... ps) {
        permissionUtil.requestPermissions(ps, new PermissionListener() {
            @Override
            public void onGranted() {
                //权限已经授予
                if (mCallBack != null) {
                    mCallBack.callBack();
                }
            }

            @Override
            public void onDenied(final List<String> deniedPermission) {
                String perstr = "";

                for (String it : deniedPermission) {

                    if (it.equals(Manifest.permission.WRITE_CONTACTS)
                            || it.equals(Manifest.permission.GET_ACCOUNTS)
                            || it.equals(Manifest.permission.READ_CONTACTS)) {

                        if (!perstr.contains("【联系人】")) {
                            perstr += "【联系人】";
                        }
                    } else if (it.equals(Manifest.permission.READ_CALL_LOG)
                            || it.equals(Manifest.permission.READ_PHONE_STATE)
                            || it.equals(Manifest.permission.CALL_PHONE)
                            || it.equals(Manifest.permission.WRITE_CALL_LOG)
                            || it.equals(Manifest.permission.USE_SIP)
                            || it.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)
                            || it.equals(Manifest.permission.ADD_VOICEMAIL)) {

                        if (!perstr.contains("【电话】")) {
                            perstr += "【电话】";
                        }

                    } else if (it.equals(Manifest.permission.READ_CALENDAR)
                            || it.equals(Manifest.permission.WRITE_CALENDAR)
                    ) {

                        if (!perstr.contains("【日历】")) {
                            perstr += "【日历】";
                        }

                    } else if (it.equals(Manifest.permission.CAMERA)
                    ) {

                        if (!perstr.contains("【相机】")) {
                            perstr += "【相机】";
                        }

                    } else if (it.equals(Manifest.permission.BODY_SENSORS)
                    ) {

                        if (!perstr.contains("【传感器】")) {
                            perstr += "【传感器】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                            || it.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {

                        if (!perstr.contains("【存储】")) {
                            perstr += "【存储】";
                        }

                    } else if (it.equals(Manifest.permission.RECORD_AUDIO)
                    ) {

                        if (!perstr.contains("【麦克风】")) {
                            perstr += "【麦克风】";
                        }

                    } else if (
                            it.equals(Manifest.permission.READ_SMS)
                                    || it.equals(Manifest.permission.RECEIVE_WAP_PUSH)
                                    || it.equals(Manifest.permission.RECEIVE_MMS)
                                    || it.equals(Manifest.permission.RECEIVE_SMS)
                                    || it.equals(Manifest.permission.SEND_SMS)

                    ) {

                        if (!perstr.contains("【短信】")) {
                            perstr += "【短信】";
                        }

                    }

                }


                //  权限申请失败
                //  为了游戏能正常运行，我们需要访问您的【】【】权限，请前往应用详情界面，打开对应权限
                //  退出	确定

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("权限申请失败");
                builder.setMessage("为了应用能正常运行，我们需要访问您的" + perstr + "权限");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 权限被拒绝
                        String[] strings = new String[deniedPermission.size()];

                        for (int i = 0; i < deniedPermission.size(); i++) {
                            strings[i] = deniedPermission.get(i);
                        }

                        requestPermission(strings);

                    }
                });

                builder.setNeutralButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //System.exit(0);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }

            @Override
            public void onShouldShowRationale(List<String> deniedPermission) {
                // 拒绝后并且不再提示


                String perstr = "";

                for (String it : deniedPermission) {

                    if (it.equals(Manifest.permission.WRITE_CONTACTS)
                            || it.equals(Manifest.permission.GET_ACCOUNTS)
                            || it.equals(Manifest.permission.READ_CONTACTS)) {

                        if (!perstr.contains("【联系人】")) {
                            perstr += "【联系人】";
                        }
                    } else if (it.equals(Manifest.permission.READ_CALL_LOG)
                            || it.equals(Manifest.permission.READ_PHONE_STATE)
                            || it.equals(Manifest.permission.CALL_PHONE)
                            || it.equals(Manifest.permission.WRITE_CALL_LOG)
                            || it.equals(Manifest.permission.USE_SIP)
                            || it.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)
                            || it.equals(Manifest.permission.ADD_VOICEMAIL)) {

                        if (!perstr.contains("【电话】")) {
                            perstr += "【电话】";
                        }

                    } else if (it.equals(Manifest.permission.READ_CALENDAR)
                            || it.equals(Manifest.permission.WRITE_CALENDAR)
                    ) {

                        if (!perstr.contains("【日历】")) {
                            perstr += "【日历】";
                        }

                    } else if (it.equals(Manifest.permission.CAMERA)
                    ) {

                        if (!perstr.contains("【相机】")) {
                            perstr += "【相机】";
                        }

                    } else if (it.equals(Manifest.permission.BODY_SENSORS)
                    ) {

                        if (!perstr.contains("【传感器】")) {
                            perstr += "【传感器】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                            || it.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {

                        if (!perstr.contains("【存储】")) {
                            perstr += "【存储】";
                        }

                    } else if (it.equals(Manifest.permission.RECORD_AUDIO)
                    ) {

                        if (!perstr.contains("【麦克风】")) {
                            perstr += "【麦克风】";
                        }

                    } else if (
                            it.equals(Manifest.permission.READ_SMS)
                                    || it.equals(Manifest.permission.RECEIVE_WAP_PUSH)
                                    || it.equals(Manifest.permission.RECEIVE_MMS)
                                    || it.equals(Manifest.permission.RECEIVE_SMS)
                                    || it.equals(Manifest.permission.SEND_SMS)

                    ) {

                        if (!perstr.contains("【短信】")) {
                            perstr += "【短信】";
                        }

                    }

                }


                //  权限申请失败
                //  为了游戏能正常运行，我们需要访问您的【】【】权限，请前往应用详情界面，打开对应权限
                //  退出	确定

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("权限申请失败");
                builder.setMessage("为了应用能正常运行，我们需要访问您的" + perstr + "权限，请前往应用详情界面，打开对应权限");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", mActivity.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            intent.putExtra("com.android.settings.ApplicationPkgName", mActivity.getPackageName());
                        }
                        mActivity.startActivity(intent);

                    }
                });

                builder.setNeutralButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });
    }


    private static void requestPermission() {


        permissionUtil.requestPermissions(ps, new PermissionListener() {
            @Override
            public void onGranted() {
                //权限已经授予
                if (mCallBack != null) {
                    mCallBack.callBack();
                }
            }

            @Override
            public void onDenied(final List<String> deniedPermission) {


                String perstr = "";

                for (String it : deniedPermission) {

                    if (it.equals(Manifest.permission.WRITE_CONTACTS)
                            || it.equals(Manifest.permission.GET_ACCOUNTS)
                            || it.equals(Manifest.permission.READ_CONTACTS)) {

                        if (!perstr.contains("【联系人】")) {
                            perstr += "【联系人】";
                        }
                    } else if (it.equals(Manifest.permission.READ_CALL_LOG)
                            || it.equals(Manifest.permission.READ_PHONE_STATE)
                            || it.equals(Manifest.permission.CALL_PHONE)
                            || it.equals(Manifest.permission.WRITE_CALL_LOG)
                            || it.equals(Manifest.permission.USE_SIP)
                            || it.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)
                            || it.equals(Manifest.permission.ADD_VOICEMAIL)) {

                        if (!perstr.contains("【电话】")) {
                            perstr += "【电话】";
                        }

                    } else if (it.equals(Manifest.permission.READ_CALENDAR)
                            || it.equals(Manifest.permission.WRITE_CALENDAR)
                    ) {

                        if (!perstr.contains("【日历】")) {
                            perstr += "【日历】";
                        }

                    } else if (it.equals(Manifest.permission.CAMERA)
                    ) {

                        if (!perstr.contains("【相机】")) {
                            perstr += "【相机】";
                        }

                    } else if (it.equals(Manifest.permission.BODY_SENSORS)
                    ) {

                        if (!perstr.contains("【传感器】")) {
                            perstr += "【传感器】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                            || it.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {

                        if (!perstr.contains("【存储】")) {
                            perstr += "【存储】";
                        }

                    } else if (it.equals(Manifest.permission.RECORD_AUDIO)
                    ) {

                        if (!perstr.contains("【麦克风】")) {
                            perstr += "【麦克风】";
                        }

                    } else if (
                            it.equals(Manifest.permission.READ_SMS)
                                    || it.equals(Manifest.permission.RECEIVE_WAP_PUSH)
                                    || it.equals(Manifest.permission.RECEIVE_MMS)
                                    || it.equals(Manifest.permission.RECEIVE_SMS)
                                    || it.equals(Manifest.permission.SEND_SMS)

                    ) {

                        if (!perstr.contains("【短信】")) {
                            perstr += "【短信】";
                        }

                    }

                }

                //  权限申请失败
                //  为了游戏能正常运行，我们需要访问您的【】【】权限，请前往应用详情界面，打开对应权限
                //  退出	确定

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("权限申请失败");
                builder.setMessage("为了应用能正常运行，我们需要访问您的" + perstr + "权限");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 权限被拒绝
                        String[] strings = new String[deniedPermission.size()];

                        for (int i = 0; i < deniedPermission.size(); i++) {
                            strings[i] = deniedPermission.get(i);
                        }

                        requestPermission(strings);

                    }
                });

                builder.setNeutralButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }

            @Override
            public void onShouldShowRationale(List<String> deniedPermission) {

                // 拒绝后并且不再提示


                String perstr = "";

                for (String it : deniedPermission) {

                    if (it.equals(Manifest.permission.WRITE_CONTACTS)
                            || it.equals(Manifest.permission.GET_ACCOUNTS)
                            || it.equals(Manifest.permission.READ_CONTACTS)) {

                        if (!perstr.contains("【联系人】")) {
                            perstr += "【联系人】";
                        }
                    } else if (it.equals(Manifest.permission.READ_CALL_LOG)
                            || it.equals(Manifest.permission.READ_PHONE_STATE)
                            || it.equals(Manifest.permission.CALL_PHONE)
                            || it.equals(Manifest.permission.WRITE_CALL_LOG)
                            || it.equals(Manifest.permission.USE_SIP)
                            || it.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)
                            || it.equals(Manifest.permission.ADD_VOICEMAIL)) {

                        if (!perstr.contains("【电话】")) {
                            perstr += "【电话】";
                        }

                    } else if (it.equals(Manifest.permission.READ_CALENDAR)
                            || it.equals(Manifest.permission.WRITE_CALENDAR)
                    ) {

                        if (!perstr.contains("【日历】")) {
                            perstr += "【日历】";
                        }

                    } else if (it.equals(Manifest.permission.CAMERA)
                    ) {

                        if (!perstr.contains("【相机】")) {
                            perstr += "【相机】";
                        }

                    } else if (it.equals(Manifest.permission.BODY_SENSORS)
                    ) {

                        if (!perstr.contains("【传感器】")) {
                            perstr += "【传感器】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                            || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        if (!perstr.contains("【位置】")) {
                            perstr += "【位置】";
                        }

                    } else if (it.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                            || it.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {

                        if (!perstr.contains("【存储】")) {
                            perstr += "【存储】";
                        }

                    } else if (it.equals(Manifest.permission.RECORD_AUDIO)
                    ) {

                        if (!perstr.contains("【麦克风】")) {
                            perstr += "【麦克风】";
                        }

                    } else if (
                            it.equals(Manifest.permission.READ_SMS)
                                    || it.equals(Manifest.permission.RECEIVE_WAP_PUSH)
                                    || it.equals(Manifest.permission.RECEIVE_MMS)
                                    || it.equals(Manifest.permission.RECEIVE_SMS)
                                    || it.equals(Manifest.permission.SEND_SMS)

                    ) {
                        if (!perstr.contains("【短信】")) {
                            perstr += "【短信】";
                        }
                    }
                }

                //  权限申请失败
                //  为了游戏能正常运行，我们需要访问您的【】【】权限，请前往应用详情界面，打开对应权限
                //  退出	确定

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("权限申请失败");
                builder.setMessage("为了应用能正常运行，我们需要访问您的" + perstr + "权限，请前往应用详情界面，打开对应权限");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", mActivity.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            intent.putExtra("com.android.settings.ApplicationPkgName", mActivity.getPackageName());
                        }
                        mActivity.startActivity(intent);

                    }
                });

                builder.setNeutralButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });
    }
}