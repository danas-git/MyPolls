package com.example.mypolls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PollPage extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poll);
		Button searchButton=(Button) findViewById(R.id.button_search_text);
		searchButton.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText poll_id=(EditText)findViewById(R.id.search_text);
				Intent searchIntent= new Intent(PollPage.this,VotingScreen.class);
				Log.d("dhanapoll",poll_id.getText().toString());
				searchIntent.putExtra("poll_id", Integer.parseInt(poll_id.getText().toString()));
				startActivity(searchIntent);
				
			}
		});
		Button createButton = (Button) findViewById(R.id.button_create_new);
		createButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent createIntent = new Intent(PollPage.this,CreateNewPoll.class);
				startActivity(createIntent);
				
			}
		});
		Button viewResultsButton = (Button) findViewById(R.id.button_view_results);
		viewResultsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText poll_id=(EditText)findViewById(R.id.search_text);
				Intent searchIntent= new Intent(PollPage.this,VoteResults.class);
				Log.d("dhanapoll",poll_id.getText().toString());
				searchIntent.putExtra("poll_id", Integer.parseInt(poll_id.getText().toString()));
				startActivity(searchIntent);
				
			}
		});
	}
}
