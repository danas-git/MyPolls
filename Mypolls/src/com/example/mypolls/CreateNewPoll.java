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
import org.json.JSONStringer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Log;

public class CreateNewPoll extends Activity {
	int i=1;
	public Handler handler2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createpoll);
		handler2=new Handler();
		Button addMoreOptions=(Button)findViewById(R.id.add_more_options);
		Button createpoll=(Button)findViewById(R.id.create_poll);
	
		ArrayList<Integer> optionlist = new ArrayList<Integer>();
		Log.d("dhanapoll", "createpoll");
		addMoreOptions.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout optionslayout=(LinearLayout)findViewById(R.id.optionslayout);
				EditText newoption = new EditText(CreateNewPoll.this);
				int id=View.generateViewId();
				newoption.setId(i);
				String optionid = "Option"+String.valueOf(i);
				newoption.setHint(optionid);
				optionslayout.addView(newoption);
				i++;

			}
		});
		createpoll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("dhanapoll", "onlick");
				// TODO Auto-generated method stub
				fnCreatePoll();
			}
		});
	}
	void fnCreatePoll(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				Log.d("dhanapoll","run");
				// TODO Auto-generated method stub
				try{
						EditText question=(EditText)findViewById(R.id.question);
						ArrayList <NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
						namevaluepairs.add(new BasicNameValuePair("userid",String.valueOf(MainActivity.gUserId)));
						namevaluepairs.add(new BasicNameValuePair("question",question.getText().toString()));
						namevaluepairs.add(new BasicNameValuePair("status","O"));
						JSONArray optionsarray=new JSONArray();
						for (int j=1;j<i;j++){
							Log.d("dhanapoll","forloop");
							EditText optiontext=(EditText)findViewById(j);
							Log.d("dhanapoll",optiontext.getText().toString());
							optionsarray.put(optiontext.getText().toString());
							//	namevaluepairs.add(new BasicNameValuePair("options[]",optiontext.getText().toString()));
						}

						namevaluepairs.add(new BasicNameValuePair("options",optionsarray.toString()));
						Log.d("dhanapoll",namevaluepairs.get(3).toString());
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://www.danas.comeze.com/insert_poll.php");
						httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
						HttpResponse httpresponse = httpclient.execute(httppost);
						HttpEntity entity=httpresponse.getEntity();
						String result=EntityUtils.toString(entity);
						Log.d("dhanapoll",result);
						JSONObject jobject=new JSONObject(result);
						final int iPollId=jobject.getInt("pollid");
						Log.d("dhanapoll",String.valueOf(iPollId));
						handler2.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Log.d("dhanapoll","debug1");
							AlertDialog.Builder pollCreateAlert=new AlertDialog.Builder(CreateNewPoll.this);
							Log.d("dhanapoll","debug2");
							pollCreateAlert.setTitle("Poll Created");
							pollCreateAlert.setMessage("Your Poll id : " + iPollId);
							pollCreateAlert.setPositiveButton("Back to main", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent mainintent = new Intent(CreateNewPoll.this,PollPage.class);
									startActivity(mainintent);


								}

							});
							pollCreateAlert.setNegativeButton("Share", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(), "Under Construction", 3000).show();
									

								}
							});
							AlertDialog alertdialog=pollCreateAlert.create();
							alertdialog.show();

							}

						});

				}catch (Exception e){
					e.printStackTrace();
				}

			}

		}).start();

	}

}
