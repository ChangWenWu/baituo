package com.yangyu.mycustomtab02;





import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.android.DetailAdapter;
import com.android.DetailEntity;
import com.example.delivery_second.Login;
import com.example.delivery_second.Navigation;
import com.example.delivery_second.R;
import com.example.delivery_second.Register_a;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Check extends Activity {
	Button checkBT;
	private ListView talkView;
	private List<DetailEntity> list = null;
	
	Button pick_btn1;
	Button pick_btn2;
	Button pick_btn3;
	Button pick_btn4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check);
		checkBT = (Button) findViewById(R.id.btn_check);
		pick_btn1 = (Button) findViewById(R.id.pick_btn1);
		pick_btn2 = (Button) findViewById(R.id.pick_btn2);
		pick_btn3 = (Button) findViewById(R.id.pick_btn3);
		pick_btn4 = (Button) findViewById(R.id.pick_btn4);
		
		/**
		 * upload the picture
		 */
		checkBT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		
						Intent intent = new Intent();
						intent.setClass(Check.this,CheckAgain.class);
						startActivity(intent);
			}
		});
		
		pick_btn1.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                startActivityForResult(intent, 1);  
                pick_btn1.setClickable(false);
                pick_btn1.setText("已选择图片");
            }  
              
        });
		
		
		pick_btn2.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                startActivityForResult(intent, 1);  
                pick_btn2.setClickable(false);
                pick_btn2.setText("已选择图片");
            }  
              
        });
		
		pick_btn3.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                startActivityForResult(intent, 1);  
                pick_btn3.setClickable(false);
                pick_btn3.setText("已选择图片");
            }  
              
        });
		
		
		pick_btn4.setOnClickListener(new Button.OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                startActivityForResult(intent, 1);  
                pick_btn4.setText("已选择图片");
            }  
              
        });
		
	}

	
	@Override  
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
            Uri uri = data.getData();  
            Log.e("uri", uri.toString());  
            ContentResolver cr = this.getContentResolver();  
            try {  
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
                
                /* 将Bitmap设定到ImageView */  
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
	
	
}