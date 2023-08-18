package com.example.sqlite_mvvm.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.sqlite_mvvm.DBHandler;
import com.example.sqlite_mvvm.model.UserData;

import java.util.List;

public class userRepository  {

    DBHandler dbHandler;

    public  void updateData(Context context, String str_updateName, String str_updateAge, int id) {
        dbHandler = new DBHandler(context);
        dbHandler.updateData(str_updateName,str_updateAge,id);
    }

    public MutableLiveData<String> deleteData(Context context, int id){

        MutableLiveData<String> liveData = new MutableLiveData<>();
        dbHandler = new DBHandler(context);
        liveData =  dbHandler.deleteUserData(id);

        return liveData;
    }

    public long addData(Context context, String str_name, String str_age){

        dbHandler = new DBHandler(context);
        long id = dbHandler.addNewData(str_name,str_age);
        return id;
    }

    public MutableLiveData<List<UserData>> readData(Context context){
        dbHandler = new DBHandler(context);
        MutableLiveData<List<UserData>> liveData = dbHandler.readData();

        //dbHandler.readData();
        return liveData;
    }

}
