package com.example.sqlite_mvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sqlite_mvvm.Adapter.userAdapter;
import com.example.sqlite_mvvm.Listener.MainListener;
import com.example.sqlite_mvvm.R;
import com.example.sqlite_mvvm.MainViewModel;
import com.example.sqlite_mvvm.databinding.ActivityMainBinding;
import com.example.sqlite_mvvm.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainListener {

    ActivityMainBinding binding;
    MainViewModel mainViewModel;
    userAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.mainListener= this;
        binding.setMainViewModel(mainViewModel);

        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false);
        binding.recyclerView.setLayoutManager(manager);

        adapter = new userAdapter(MainActivity.this,new ArrayList<>(),mainViewModel);
        binding.recyclerView.setAdapter(adapter);
        mainViewModel.setUserList(MainActivity.this);
    }

    @Override
    public void addUser(UserData userData) {
        adapter.addUserData(userData);
    }

    @Override
    public void getData(MutableLiveData<List<UserData>> listMutableLiveData) {
        listMutableLiveData.observe(this, new Observer<List<UserData>>() {
            @Override
            public void onChanged(List<UserData> userData) {

                adapter.getAllData(userData);
            }
        });
    }

    @Override
    public void updateDatatoList(UserData userData1) {

        adapter.updateList(userData1);
    }

    @Override
    public void deleteDatatoList(UserData userData, MutableLiveData<String> s) {

        s.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equalsIgnoreCase("Delete Successfully")){
                    adapter.removeUser(userData);
                }
            }
        });
    }

//    @Override
//    public void updateDatatoList(MutableLiveData<List<UserData>> updateLiveData) {
//        updateLiveData.observe(this, new Observer<List<UserData>>() {
//            @Override
//            public void onChanged(List<UserData> userData) {
//
//                adapter.updateList(userData);
//
//            }
//        });
    }

