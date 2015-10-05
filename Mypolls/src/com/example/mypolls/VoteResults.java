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

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VoteResults extends Activity {
	public Handler handler;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voteresults);
		final LinearLayout resultslayout=(LinearLayout)findViewById(R.id.resultslayout);
		handler=new Handler();
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					ArrayList <NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
					int poll_id=getIntent().getIntExtra("poll_id", 1);
					Log.d("dhanapoll","poll_id"+poll_id);
					namevaluepairs.add(new BasicNameValuePair("pollid",String.valueOf(poll_id)));
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://www.danas.comeze.com/voteresult.php");
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
							final String question = jObj.getString("question");
							handler.post(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									TextView questiontext=(TextView)findViewById(R.id.votescreenquestionresults);
									questiontext.setText(question);
								}
								
							});
							Log.d("dhanapoll", "question"+question );
						}
						else{
							final String option = jObj.getString("options");
							final String countstring = jObj.getString("votes");
							handler.post(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									TextView optionstext=new TextView(VoteResults.this);
									optionstext.setText(option +"("+countstring+")");
									resultslayout.addView(optionstext);
									Log.d("dhanapoll","temp2"+option+countstring);
								
								}
								
							});
						}
						
						
					}
					
					}catch(Exception e){
						e.printStackTrace();
					}

			}
			
		}).start();
	}

}
