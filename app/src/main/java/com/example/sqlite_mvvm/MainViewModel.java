package com.example.sqlite_mvvm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sqlite_mvvm.Listener.MainListener;
import com.example.sqlite_mvvm.Repository.userRepository;
import com.example.sqlite_mvvm.model.UserData;

import java.util.List;

public class MainViewModel extends ViewModel {

   public MainListener mainListener;
    public void onClickAddData(Context context){

        Dialog dialog = new Dialog(context);

        Toast.makeText(context, "Add your details", Toast.LENGTH_SHORT).show();

        dialog.setContentView(R.layout.activity_pop_up);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button Add = dialog.findViewById(R.id.add_btn);
        EditText Name = dialog.findViewById(R.id.name);
        EditText Age = dialog.findViewById(R.id.age);

        userRepository repository = new userRepository();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Str_Name = Name.getText().toString();
                String Str_Age =  Age.getText().toString();

                if(TextUtils.isEmpty(Str_Name)){
                    Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Str_Age)){
                    Toast.makeText(context, "Enter Age", Toast.LENGTH_SHORT).show();
                }
                else{

                    int id = Math.toIntExact(repository.addData(context,Str_Name,Str_Age));
                    UserData userData = new UserData(Str_Name,Str_Age,id);

                    mainListener.addUser(userData);
                    dialog.dismiss();

                    Toast.makeText(context, "User Detail has been added.", Toast.LENGTH_SHORT).show();

                    Name.setText("");
                    Age.setText("");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        //mainListener.onClickAddData();
    }

    public void editButtonClick(Context context,UserData userData){

        //mainListener.editButtonClick(view.getContext(), userData,view);

        Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.edit_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button update = dialog.findViewById(R.id.update_data);
        EditText name_edt = dialog.findViewById(R.id.name_edt);
        EditText age_edt = dialog.findViewById(R.id.age_edt);

        name_edt.setText(userData.getStr_name());
        age_edt.setText(userData.getStr_age());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRepository repository = new userRepository();

                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();

                String str_updateName = name_edt.getText().toString();
                String str_updateAge = age_edt.getText().toString();

                if (TextUtils.isEmpty(str_updateName)) {
                    name_edt.setError("Please enter Name!");
                } else if (TextUtils.isEmpty(str_updateAge)) {
                    age_edt.setError("Please enter your age!");
                } else {
                    UserData userData1 = new UserData(str_updateName,str_updateAge,userData.getId());

                    repository.updateData(context,str_updateName,str_updateAge,userData1.getId());
                    mainListener.updateDatatoList(userData1);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void deleteButtonClick(Context context,UserData userData){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Are you sure want to delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MutableLiveData<String> s = new userRepository().deleteData(context,userData.getId());

                mainListener.deleteDatatoList(userData,s);

                Toast.makeText(context, "Delete Data!!", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void setUserList(Context context) {

        MutableLiveData<List<UserData>> listMutableLiveData = new userRepository().readData(context);
        mainListener.getData(listMutableLiveData);

    }
}
