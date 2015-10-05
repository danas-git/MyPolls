package com.example.mypolls;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Button b3 = (Button) findViewById(R.id.button_clear);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText username=(EditText)findViewById(R.id.username_edit);
				EditText password=(EditText) findViewById(R.id.password_edit);
				EditText age=(EditText)findViewById(R.id.age_edit);
				username.setText("");
				password.setText("");
				age.setText("");
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button_ok);
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insert_user();
				
			}
		});
		
	}
	void insert_user(){
		Button b2 = (Button) findViewById(R.id.button_ok);
		b2.setVisibility(View.INVISIBLE);
		Thread thread = new Thread(){
			
			public void run(){
				try{
					EditText username=(EditText)findViewById(R.id.username_edit);
					EditText password=(EditText)findViewById(R.id.password_edit);
					EditText age=(EditText)findViewById(R.id.age_edit);
					ArrayList <NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
					namevaluepairs.add(new BasicNameValuePair("username",username.getText().toString()));
					namevaluepairs.add(new BasicNameValuePair("password",password.getText().toString()));
					namevaluepairs.add(new BasicNameValuePair("age",age.getText().toString()));
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://www.danas.comeze.com/Register.php");
					httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
					HttpResponse httpresponse = httpclient.execute(httppost);
				}catch(Exception e){
					Log.e("Log_tag","Error in Http" + e.toString());
		
				}
			
			}
		};
		thread.start();
	}

}
