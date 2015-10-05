package com.example.mypolls;

import java.lang.reflect.Array;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class VotingScreen extends Activity{
	int i=1;
	ArrayList<String> radioarray;
	RadioGroup radiogroup ;
	public Handler handler3;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.votescreen);
		Log.d("dhanapoll","came to vote");
		handler3 = new Handler();
		radiogroup = (RadioGroup)findViewById(R.id.radiogroup_options);
		radioarray = new ArrayList<String>();
		
		
		VotingAsync voting = new VotingAsync();
		voting.execute("");
		Button button_vote =(Button)findViewById(R.id.button_vote);
		button_vote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							int checkedid = radiogroup.getCheckedRadioButtonId();
							RadioButton checkedbutton = (RadioButton)findViewById(checkedid);
							//Toast.makeText(getApplicationContext(), checkedbutton.getText().toString(), 3000).show();
							ArrayList <NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
							int poll_id=getIntent().getIntExtra("poll_id", 1);
							Log.d("dhanapoll","poll_id"+poll_id);
							Log.d("dhanapoll","option"+checkedbutton.getText().toString());
							namevaluepairs.add(new BasicNameValuePair("voteoption",checkedbutton.getText().toString()));
							namevaluepairs.add(new BasicNameValuePair("pollid",String.valueOf(poll_id)));
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost("http://www.danas.comeze.com/recordvote.php");
							httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
							HttpResponse httpresponse = httpclient.execute(httppost);
							HttpEntity entity=httpresponse.getEntity();
							String result=EntityUtils.toString(entity);
							Log.d("dhanapoll","resultofvote" + result);
							handler3.post(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								AlertDialog.Builder pollCreateAlert=new AlertDialog.Builder(VotingScreen.this);
								Log.d("dhanapoll","debug2");
								pollCreateAlert.setTitle("Vote Confirmation");
								pollCreateAlert.setMessage("Vote Recorded. Thanks for Voting");
								pollCreateAlert.setPositiveButton("Back to main", new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent mainintent = new Intent(VotingScreen.this,PollPage.class);
										startActivity(mainintent);
				
				
									}
				
								});
								pollCreateAlert.setNegativeButton("View Results", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										int poll_id=getIntent().getIntExtra("poll_id", 1);
										Log.d("dhanapoll","pollid before results"+String.valueOf(poll_id));
										Intent mainintent = new Intent(VotingScreen.this,VoteResults.class);
										mainintent.putExtra("poll_id", poll_id);
										startActivity(mainintent);
				
										
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
		});

	}
	public class VotingAsync extends AsyncTask<String,String,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				ArrayList <NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
				int poll_id=getIntent().getIntExtra("poll_id", 1);
				Log.d("dhanapoll","poll_id"+poll_id);
				namevaluepairs.add(new BasicNameValuePair("userid",String.valueOf(MainActivity.gUserId)));
				namevaluepairs.add(new BasicNameValuePair("pollid",String.valueOf(poll_id)));
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://www.danas.comeze.com/getpolldetails.php");
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				HttpResponse httpresponse = httpclient.execute(httppost);
				HttpEntity entity=httpresponse.getEntity();
				String result=EntityUtils.toString(entity);
				Log.d("dhanapoll",result);
				//JSONObject jObject=new JSONObject(result);
				JSONArray jArray = new JSONArray(result);
				Log.d("dhanapoll","dhana1");
				Log.d("dhanapoll",String.valueOf(jArray.length()));
				int i;
				for(i=0;i<jArray.length();i++){
					Log.d("dhanapoll","dhana2");
					JSONObject jObj = jArray.getJSONObject(i);
					if(i==0){
						String temp = jObj.getString("question");
						radioarray.add(temp);
						Log.d("dhanapoll", "temp"+temp );
					}
					else{
						String temp2 = jObj.getString("options");
						radioarray.add(temp2);
						Log.d("dhanapoll","temp2"+temp2);
						Log.d("dhanapoll","size"+radioarray.size());
					}
					
					
				}
				
				
			}catch (Exception e){
				e.printStackTrace();
			}
		
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			int j=0;
			Log.d("dhanapoll","size"+radioarray.size());
			for (j=0;j<radioarray.size();j++){
				if(j==0){
					TextView questiontext = (TextView)findViewById(R.id.votescreenquestion);
					Log.d("dhanapoll","radio"+radioarray.get(j));
					questiontext.setText(radioarray.get(j));
					
				}else{
					Log.d("dhanapoll","enter else");
					RadioButton newradiobutton = new RadioButton(VotingScreen.this);
					Log.d("dhanapoll","after radio declaration");
					int id=View.generateViewId();
					Log.d("dhanapoll","before idset");
					newradiobutton.setId(i);
					Log.d("dhanapoll","after id set");
					Log.d("dhanapoll","radio1"+radioarray.get(j));
					newradiobutton.setText(radioarray.get(j));
					radiogroup.addView(newradiobutton);
					i++;
				}
			}
			
			
		}

	}
}
	

