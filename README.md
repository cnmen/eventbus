### Hey, Do you really know about EventBus ?
#### If you not sure, please click here : [Eventbus](https://www.cmonbaby.com/posts/netease_eventbus.html)

## EventBus ![Build Status](https://travis-ci.org/greenrobot/EventBus.svg?branch=master)

* EventBus is a publish/subscribe event bus for Android and Java.
* simplifies the communication between components
* makes your code simpler
* is fast
* is tiny (~50k jar)
* has advanced features like delivery threads, subscriber priorities, etc.

## EventBus in 4 steps

#### 1. configuration app/build.gradle:
```gradle
android {
    // ......
    defaultConfig {
        // ......

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [packageName: 'com.cmonbaby.eventbus.apt', className: 'EventBusIndex']
            }
        }
    }
    buildTypes {
        release {
            // ......
        }
    }
}
```

#### 2. Prepare subscribers: Declare and annotate your subscribing method, optionally specify a thread mode:
```java
@Subscribe(threadMode = ThreadMode.MAIN)
public void getUser(UserInfo info) { }
```

#### 3. Post events:
```java
EventBus.getDefault().post(new UserInfo("simon", 35));
// or
EventBus.getDefault().postSticky(new UserInfo("simon", 35));
```

#### 4. About Parameters
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create indexes for apt
        EventBus.getDefault().addIndex(new EventBusIndex());
        // register listener
        EventBus.getDefault().register(this);
    }

    // post event
    public void post() {
        EventBus.getDefault().post(new UserInfo("simon", 35));
    }

    // post sticky event
    public void sticky() {
        EventBus.getDefault().postSticky(new UserInfo("simon", 35));
    }

    // The higher number the quantity, the higher the priority
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void getUser(UserInfo info) { // subscribe method
        // Do something
    }

    // subscribe sticky method
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sticky(UserInfo user) {
        Log.e("sticky", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // destroy eventbus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        EventBus.clearCaches();
    }
}
```

## Add EventBus to your project
<a href="https://www.cmonbaby.com/posts/netease_modular.html">
<img src="https://img.shields.io/bintray/v/cmonbaby/simon/eventbus_annotation?label=maven-central"></a>

Via Gradle:
```gradle
implementation 'com.cmonbaby.eventbus.core:eventbus_core:1.0.0'
implementation 'com.cmonbaby.eventbus.annotation:eventbus_annotation:1.0.0'
annotationProcessor 'com.cmonbaby.eventbus.compiler:eventbus_compiler:1.0.0'
```

Via Maven:
```xml
<dependency>
    <groupId>com.cmonbaby.eventbus.annotation</groupId>
    <artifactId>eventbus_annotation</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.cmonbaby.eventbus.compiler</groupId>
    <artifactId>eventbus_compiler</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.cmonbaby.eventbus.core</groupId>
    <artifactId>eventbus_core</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Copyright (C) 2013-2020 Markus Junginger, Simon (https://www.cmonbaby.com)  
EventBus binaries and source code can be used according to the [Apache License, Version 2.0](LICENSE).
