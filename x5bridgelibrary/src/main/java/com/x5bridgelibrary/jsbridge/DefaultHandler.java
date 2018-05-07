package com.x5bridgelibrary.jsbridge;

/**
 * Created by zhaochong on 4/5/18.
 */
public class DefaultHandler implements BridgeHandler{

	String TAG = "DefaultHandler";
	
	@Override
	public void handler(String data, CallBackFunction function) {
		if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

}
