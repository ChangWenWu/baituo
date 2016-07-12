package com.yangyu.mycustomtab02;

import com.example.delivery_second.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentPage1 extends Fragment{
	Button btn_inquiry;
	Button btn_otherinquiry;
	EditText et_tel;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_1, container, false);
		et_tel=(EditText)v.findViewById(R.id.et_tel);
		 btn_inquiry=(Button)v.findViewById(R.id. btn_inquiry);
		 btn_otherinquiry=(Button)v.findViewById(R.id. btn_otherinquiry);
		return inflater.inflate(R.layout.fragment_1, null);		
	}	
	
}
