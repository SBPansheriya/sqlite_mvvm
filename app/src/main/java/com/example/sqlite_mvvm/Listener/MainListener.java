package com.example.sqlite_mvvm.Listener;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.sqlite_mvvm.model.UserData;

import java.util.List;

public interface MainListener {


    void addUser(UserData id);

    void getData(MutableLiveData<List<UserData>> listMutableLiveData);


    void updateDatatoList(UserData userData1);

    void deleteDatatoList(UserData userData, MutableLiveData<String> s);

}
