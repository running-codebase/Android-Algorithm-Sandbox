package com.travis.awesome.algorithmsandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	
	private Button launch_dijkstras;
	private Button launch_sort;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		launch_dijkstras = (Button) findViewById(R.id.launch_dijkstras);
		launch_sort = (Button) findViewById(R.id.launch_sort);

		launch_dijkstras.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(MainActivity.this, DijkstrasActivity.class);
				startActivity(intent);
			
			}
		});
		
		launch_sort.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(MainActivity.this, SortActivity.class);
				startActivity(intent);
			
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
