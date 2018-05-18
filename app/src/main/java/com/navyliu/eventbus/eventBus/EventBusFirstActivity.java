package com.navyliu.eventbus.eventBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.navyliu.eventbus.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018-04-25.
 *
 * @auther navyLiu
 * @Email navyliu666666@gmail.com
 */

public class EventBusFirstActivity extends Activity implements View.OnClickListener {

	private TextView eventBusTxt;
	private Button eventBusBtn;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_bus);

		init();

		EventBus.getDefault().register(this);
	}

	private void init() {
		eventBusTxt = (TextView) this.findViewById(R.id.txt_event_bus);
		eventBusBtn = (Button) this.findViewById(R.id.btn_eventbus);
		eventBusBtn.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_eventbus:
				startEventBusActivity();
				break;
		}
	}

	public void startEventBusActivity(){
		Intent intent = new Intent(this, EventBusSecondActivity.class);
		startActivity(intent);
	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void BusEvent(MessageEvent messageEvent){
		eventBusTxt.setText(messageEvent.getMessage());
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (EventBus.getDefault().isRegistered(this)){
			EventBus.getDefault().unregister(this);
		}
	}




}
