package com.szxgm.gusustreet.ui.activity.attendance;

import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAttendanceMainBinding;
import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 考勤主页.
 */
public class AttendanceActivity extends RequestBVActivity<ActivityAttendanceMainBinding> {

    public static Class clz = AttendanceActivity.class;

    @Override
    public void initView() {
        binding.navView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void onClick(View view){
    }

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_main;
    }
}
