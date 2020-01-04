import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_these_health/flutter_these_health.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  int _myStep = 0;
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await FlutterTheseHealth.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }



  Future<void> isEuropeUser() async {
    int myStep = 0;
    // Native channel
    const platform = const MethodChannel("com.today.step");
    try {
      myStep = await platform.invokeMethod("myStep");
    } on PlatformException catch (e) {
      print(e.toString());
    }
    print(myStep);


    setState(() {
      _myStep = myStep;
    });


  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Column(
          children: <Widget>[
            new Center(
              child: new RaisedButton(
                onPressed: (){
                  this.isEuropeUser();
                },
                color: Colors.blueAccent,//按钮的背景颜色
                padding: EdgeInsets.all(50.0),//按钮距离里面内容的内边距
                child: new Text(_myStep.toString()),
                textColor: Colors.white,//文字的颜色
                textTheme:ButtonTextTheme.normal ,//按钮的主题
                onHighlightChanged: (bool b){//水波纹高亮变化回调
                },
                disabledTextColor: Colors.black54,//按钮禁用时候文字的颜色
                disabledColor: Colors.black54,//按钮被禁用的时候显示的颜色
                highlightColor: Colors.yellowAccent,//点击或者toch控件高亮的时候显示在控件上面，水波纹下面的颜色
                splashColor: Colors.white,//水波纹的颜色
                colorBrightness: Brightness.light,//按钮主题高亮
                elevation: 10.0,//按钮下面的阴影
                highlightElevation: 10.0,//高亮时候的阴影
                disabledElevation: 10.0,//按下的时候的阴影
//              shape:,//设置形状  LearnMaterial中有详解
              ),
            )
          ],
        ),
      ),
    );
  }
}
