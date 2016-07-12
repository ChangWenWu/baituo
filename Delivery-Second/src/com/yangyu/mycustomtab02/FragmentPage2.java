package com.yangyu.mycustomtab02;

import com.example.delivery_second.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentPage2 extends Fragment{
	Button btn_check;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_2, container, false);
		 btn_check=(Button)v.findViewById(R.id. btn_check);
		
		
		return inflater.inflate(R.layout.fragment_2, null);		
	}	
}