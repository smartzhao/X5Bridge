# X5Bridge
# X5Bridge [![](https://jitpack.io/v/zorozhao/X5Bridge.svg)](https://jitpack.io/#zorozhao/X5Bridge)
The library of the bridge between Android native and react native


## Usage

## JitPack.io

I strongly recommend https://jitpack.io

```groovy
repositories {
    // ...
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.zorozhao:X5Bridge:0.1'
}
```

## Use it in Java

add com.x5bridgelibrary.jsbridge.BridgeWebViewto your layout, it is inherited from WebView.

```java

> manifests

            <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
            <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
            <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
            <uses-permission android:name="android.permission.READ_PHONE_STATE" />
            <uses-permission android:name="android.permission.READ_SETTINGS" />
            <uses-permission
                android:name="android.permission.WRITE_SETTINGS"
                tools:ignore="ProtectedPermissions" />
            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission
                android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
                tools:ignore="ProtectedPermissions" />
            <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

            <!-- 硬件加速对X5视频播放非常重要，建议开启 -->
            <uses-permission android:name="android.permission.GET_TASKS" />


> application

    X5bridgeManager.getInstance().preInitX5Core(this);

> activity

            MainActivity extends AppCompatActivity implements X5EventListener

            @Override
            public int obtainLayoutId() {
                return R.id.x5webview;
            }

            @Override
            public void onPageStarted(String url, Bitmap favicon) {
                Log.d(TAG, "onPageStarted: " + url);
            }

            @Override
            public void onPageFinished(String url) {
                Log.d(TAG, "onPageFinished: " + url);
            }

            public X5HandlerListener initX5HandlerListener() {
                  x5HandlerListener = X5bridgeManager
                      .getInstance()
                      .setX5EventListener(this)
                      .setActivity(MainActivity.this)
                      .getX5HandlerListener();
                 return x5HandlerListener;
    }
```

### Register a Java handler function so that js can call

```java

    x5HandlerListener.registerHandler("submitFromWeb", new BridgeHandler() {
        @Override
        public void handler(String data, CallBackFunction function) {
            Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
            function.onCallBack("submitFromWeb exe, response data from Java");
        }
    });

```

js can call this Java handler method "submitFromWeb" through:

```javascript

    WebViewJavascriptBridge.callHandler(
        'submitFromWeb'
        , {'param': str1}
        , function(responseData) {
            document.getElementById("show").innerHTML = "send get responseData from java, data = " + responseData
        }
    );

```

You can set a default handler in Java, so that js can send message to Java without assigned handlerName

```java

        Already built in the three party Library
    //   webView.setDefaultHandler(new DefaultHandler());

```

```javascript

    window.WebViewJavascriptBridge.send(
        data
        , function(responseData) {
            document.getElementById("show").innerHTML = "repsonseData from java, data = " + responseData
        }
    );

```

### Register a JavaScript handler function so that Java can call

```javascript

    WebViewJavascriptBridge.registerHandler("functionInJs", function(data, responseCallback) {
        document.getElementById("show").innerHTML = ("data from Java: = " + data);
        var responseData = "Javascript Says Right back aka!";
        responseCallback(responseData);
    });

```

Java can call this js handler function "functionInJs" through:

```java

    x5HandlerListener.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
        @Override
        public void onCallBack(String data) {

        }
    });

```
You can also define a default handler use init method, so that Java can send message to js without assigned handlerName

for example:

```javascript

    bridge.init(function(message, responseCallback) {
        console.log('JS got a message', message);
        var data = {
            'Javascript Responds': 'Wee!'
        };
        console.log('JS responding with', data);
        responseCallback(data);
    });

```

```java
    x5HandlerListener.send("hello");
```

will print 'JS got a message hello' and 'JS responding with' in webview console.

## Notice

This lib will inject a WebViewJavascriptBridge Object to window object.
So in your js, before use WebViewJavascriptBridge, you must detect if WebViewJavascriptBridge exist.
If WebViewJavascriptBridge does not exit, you can listen to WebViewJavascriptBridgeReady event, as the blow code shows:

```javascript

    if (window.WebViewJavascriptBridge) {
        //do your work here
    } else {
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                //do your work here
            },
            false
        );
    }

```

## License

This project is licensed under the terms of the apache 2.0 license.