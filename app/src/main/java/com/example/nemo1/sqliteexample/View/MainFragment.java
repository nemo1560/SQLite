package com.example.nemo1.sqliteexample.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.nemo1.sqliteexample.Model.DBProcess;
import com.example.nemo1.sqliteexample.R;
import com.example.nemo1.sqliteexample.Interface.SendData;

public class MainFragment extends Fragment implements View.OnClickListener {
    private Button trialBtn;
    private SendData sendData;

    public MainFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendData = (SendData) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        trialBtn = view.findViewById(R.id.trialBtn);
        initEvent();
        return view;
    }

    private void initEvent() {
        trialBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == trialBtn.getId()){
            sendData.onCallBack();
        }
    }


}
