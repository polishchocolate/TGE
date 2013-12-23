package com.kyler.mbq.tge.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.kyler.mbq.tge.R;
import com.kyler.mbq.tge.activities.AboutApp;

public class Welcome extends Fragment implements OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.welcome, container, false);
        Button buttonSayHi = (Button) view.findViewById(R.id.welcomeButton);
        buttonSayHi.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.welcomeButton:
            	Intent aboutThisApp = new Intent(getActivity(), AboutApp.class);
            	startActivity(aboutThisApp);
            	/* getActivity().overridePendingTransition(R.anim.ltr,
                        R.anim.rtl);
            	getActivity().finish(); */
            break;
        }   
    }
}