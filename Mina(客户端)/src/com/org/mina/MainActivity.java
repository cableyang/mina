package com.org.mina;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity{

	private ListView listView;
	
	private SimpleAdapter simpleAdapter;
	
	private ArrayList<HashMap<String, Object>> listItem;
	
    private NioSocketConnector connector = new NioSocketConnector();     
    private DefaultIoFilterChainBuilder chain = connector.getFilterChain();
    
   /* private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			try {
				listItem = new ArrayList<HashMap<String,Object>>();
				
				JSONObject jsonObject = new JSONObject(msg.obj.toString());
				JSONArray jsonArray = jsonObject.getJSONArray("FileData");
				for(int i = 0;i<jsonArray.length();i++)
				{
					JSONObject object = (JSONObject) jsonArray.get(i);
					HashMap<String, Object> map = new HashMap<String, Object>();  
		            map.put("info_no", object.get("info_no"));  
		            map.put("fileName", object.get("File_Type_name"));  
		            map.put("Insert_Operator", object.get("Insert_Operator"));  
		            listItem.add(map);
				}
				simpleAdapter.notifyDataSetChanged();
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
    	
    };*/
    
    private BroadcastReceiver PriceReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
	        if(intent.getAction().equals("com.mina.broadcast"))
	        { 
	        	String msg = intent.getStringExtra("message");
	        	try {
	        		
	        	listItem.clear();
	        		
	        	JSONObject jsonObject = new JSONObject(msg);
				JSONArray jsonArray = jsonObject.getJSONArray("FileData");
				for(int i = 0;i<jsonArray.length();i++)
				{
					JSONObject object = (JSONObject) jsonArray.get(i);
					HashMap<String, Object> map = new HashMap<String, Object>();  
		            map.put("info_no", object.get("info_no"));  
		            map.put("fileName", object.get("File_Type_name"));  
		            map.put("Insert_Operator", object.get("Insert_Operator"));  
		            listItem.add(map);
				}
				simpleAdapter.notifyDataSetChanged();
				
	        	} catch (JSONException e) {
					e.printStackTrace();
				}
	        }
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		java.lang.System.setProperty("java.net.preferIPv4Stack", "true"); 
		java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
		
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView);
		listItem = new ArrayList<HashMap<String,Object>>();
		simpleAdapter = new SimpleAdapter (this,listItem,R.layout.listitem, 
				new String[]{"info_no","fileName","Insert_Operator"}, 
				new int[]{R.id.textView01,R.id.textView02,R.id.textView03});
		listView.setAdapter(simpleAdapter);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
			 
				chain.addLast("myChin", new ProtocolCodecFilter(     
		                new ObjectSerializationCodecFactory()));   
				
				ClientHandler clientHandler = new ClientHandler(MainActivity.this);
		        connector.setHandler(clientHandler);     
		        connector.setConnectTimeout(30);     
		        ConnectFuture cf = connector.connect(new InetSocketAddress("192.168.1.104",4000));   
				
		        cf.join();
		        
		       System.out.println("isConnected:"+cf.isConnected());
		        
		        if(!cf.isConnected())
		        {
		        	System.out.println("连接服务器失败！！！");
		        }
		        else
		        {
		        	System.out.println("连接成功！！！");
		        }
			}
			}).start(); 
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void close() {
		Intent i = new Intent();
		i.setAction("com.mina.broadcast");
		sendBroadcast(i);
		finish();
	}

	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.mina.broadcast");
		registerReceiver(PriceReceiver, intentFilter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(PriceReceiver);
	}
	
	
}
