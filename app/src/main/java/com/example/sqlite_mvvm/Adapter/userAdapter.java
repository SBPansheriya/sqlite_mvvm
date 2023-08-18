package com.example.sqlite_mvvm.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite_mvvm.R;
import com.example.sqlite_mvvm.MainViewModel;
import com.example.sqlite_mvvm.model.UserData;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private List<UserData> userDataList;
    private Activity activity;
    MainViewModel mainViewModel;

    public userAdapter( Activity activity,List<UserData> userDataList, MainViewModel mainViewModel) {
        this.userDataList = userDataList;
        this.activity = activity;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public userAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.ViewHolder holder, int position) {

        holder.getBinding().setVariable(BR.model, userDataList.get(position));
        holder.getBinding().setVariable(BR.mainViewModel,mainViewModel);
        holder.getBinding().executePendingBindings();

//        UserData modal = userData_ArrayList.get(position);
//        holder.Name.setText(modal.getStr_name());
//        holder.Age.setText(modal.getStr_age());

       // name = modal.getStr_name();

    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public void getAllData(List<UserData> listMutableLiveData) {
        this.userDataList.clear();
        this.userDataList = listMutableLiveData;
        notifyDataSetChanged();
    }

    public void addUserData(UserData userData) {

        this.userDataList.add(userData);
        notifyDataSetChanged();
    }

    public void updateList(UserData userData1) {

        for(int i= 0;i<userDataList.size();i++){

            if(userDataList.get(i).getId() == userData1.getId())
            {
                userDataList.remove(i);
                userDataList.add(i,userData1);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void removeUser(UserData userData) {

        this.userDataList.remove(userData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;
        ImageView edit,delete;
        TextView Name,Age;

        public ViewHolder(@NonNull View inflate) {
            super(inflate);
            binding = DataBindingUtil.bind(itemView);

            Name = inflate.findViewById(R.id.name1);
            Age = inflate.findViewById(R.id.age1);
            delete = inflate.findViewById(R.id.delete_data);
            edit = inflate.findViewById(R.id.edit_data);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
