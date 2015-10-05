package com.example.mypolls;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public Handler handler;
	public static int gUserId=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		handler=new Handler();
		Button b3 = (Button) findViewById(R.id.button_clear);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText username=(EditText)findViewById(R.id.user_new);
				EditText password=(EditText) findViewById(R.id.password_new);
				username.setText("");
				password.setText("");
			}
		});
		Button b2 =(Button) findViewById(R.id.button_ok);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "onclick", 3000).show();

				login_validation();
				
			}
		});
		Button b1 = (Button)findViewById(R.id.register_new);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startRegister();
				}
		});
		
	}
	void login_validation(){
		EditText username=(EditText)findViewById(R.id.user_new);
		EditText password=(EditText) findViewById(R.id.password_new);
		if((username.length()<1)||(password.length()<1)){
			Toast.makeText(getApplicationContext(), "Enter username and password", 3000).show();

			Intent mainintent = new Intent(this,MainActivity.class);
			this.startActivity(mainintent);
		}
		else{	
				new Thread(new Runnable(){
				@Override
				public void run(){
								try{
										EditText username=(EditText)findViewById(R.id.user_new);
										EditText password=(EditText) findViewById(R.id.password_new);
						
										ArrayList <NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
										namevaluepairs.add(new BasicNameValuePair("username",username.getText().toString()));
										namevaluepairs.add(new BasicNameValuePair("password",password.getText().toString()));
										HttpClient httpclient = new DefaultHttpClient();
										HttpPost httppost = new HttpPost("http://www.danas.comeze.com/login.php");
										httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
										HttpResponse httpresponse = httpclient.execute(httppost);
										HttpEntity entity=httpresponse.getEntity();
										String result=EntityUtils.toString(entity);
										Log.d("dhanapoll",result);
										JSONObject jobject=new JSONObject(result);
										System.out.println("dhanaa");
										if(jobject.length()==0){
											handler.post(new Runnable(){
												@Override
												public void run(){
										
												
												Intent mainintent = new Intent(MainActivity.this,MainActivity.class);
												MainActivity.this.startActivity(mainintent);
												Toast.makeText(getApplicationContext(), "User doesn't exit", 3000).show();

												}
											});
										}
										if(jobject.length()!=0){
											gUserId=jobject.getInt("userid");
											handler.post(new Runnable(){
												@Override
												public void run(){
													
													Toast.makeText(getApplicationContext(), "Proper input", 3000).show();	
												Intent pollintent = new Intent(MainActivity.this,PollPage.class);
												MainActivity.this.startActivity(pollintent);
												}
											});

										}	
								}catch (Exception e){
										e.printStackTrace();
								}
						}
			}).start();
		}
	}
	void startRegister(){
		Intent registerintent = new Intent(this,Register.class);
		this.startActivity(registerintent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
