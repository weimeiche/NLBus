package com.navyliu.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.navyliu.eventbus.bus.DNBus;
import com.navyliu.eventbus.bus.Subscribe;
import com.navyliu.eventbus.eventBus.EventBusFirstActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		testEvetBus();

		DNBus.getDefault().register(this);
		DNBus.getDefault().post("1", "大保健是什么？", 1);

	}


	@Subscribe({"1"})
	private void test(String msg, Integer integer) {
		Log.e(this.getClass().getName(), "test=======msg:" + msg + ", msg1:" + integer);
	}

	@Subscribe({"3"})
	private void test2(String msg, Integer integer) {
		Log.e(this.getClass().getName(), "test2=======msg:" + msg + ", msg1:" + integer);
	}


	private void testEvetBus() {
		Intent intent = new Intent(this, EventBusFirstActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DNBus.getDefault().unregister(this);
	}
}
