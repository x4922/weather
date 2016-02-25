package com.xw.weathe;

import java.io.StringReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response=(String) msg.obj;
				responseText.setText(response);
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	private void sendRequestWithHttpClient() {
		// TODO 自动生成的方法存根
		new Thread(new Runnable() {
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://192.168.199.210/get_data.xml");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						// 请求和响应都成功了
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity,
								"utf-8");
						parseXMLWithPull(response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			private void parseXMLWithPull(String response) {
				// TODO 自动生成的方法存根
				try {
					XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
					XmlPullParser xmlPullParser =factory.newPullParser();
					xmlPullParser.setInput(new StringReader(response));
					int eventType = xmlPullParser.getEventType();
					String id="";
					String name ="";
					String version="";
					while(eventType!=xmlPullParser.END_DOCUMENT){
						String nodeName=xmlPullParser.getName();
						switch (eventType) {
						case XmlPullParser.START_TAG:{
							if("id".equals(nodeName)){
								id=xmlPullParser.nextText();
							}else if ("name".equals(nodeName)){
								name=xmlPullParser.nextText();
							}else if ("version".equals(nodeName)) {
								version=xmlPullParser.nextText();
							}
							break;}
						case XmlPullParser.END_TAG:{
							if ("app".equals(nodeName)) {
								Log.d("MyActivity", "id is "+id);
								Log.d("MyActivity", "name is "+name);
								Log.d("MyActivity", "version is "+version);
							} 
							break;
						}

						default:
							break;
						}
						eventType=xmlPullParser.next();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
}
