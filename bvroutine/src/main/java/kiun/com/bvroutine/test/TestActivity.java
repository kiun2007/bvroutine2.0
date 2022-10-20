package kiun.com.bvroutine.test;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.databinding.ActivityTestBinding;

/**
 * 文 件 名: Test
 * 作 者: 刘春杰
 * 创建日期: 2020/7/27 21:19
 * 说明: 测试页面
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        List<String> list = new LinkedList<>();
        list.add("abc");
        binding.setList(list);
    }
}