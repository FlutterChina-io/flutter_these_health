package io.flutterchina.flutter_these_health_example;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  private int mStepSum;
  private static ISportStepInterface iSportStepInterface;

  public static ISportStepInterface getISportStepInterface(){
      return iSportStepInterface;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //初始化计步模块
    TodayStepManager.startTodayStepService(getApplication());
    //开启计步Service，同时绑定Activity进行aidl通信
    Intent intent = new Intent(this, TodayStepService.class);
        startService(intent);
    bindService(intent, new ServiceConnection() {
      @Override
      public void onServiceConnected(ComponentName name, IBinder service) {
        //Activity和Service通过aidl进行通信
        iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
      }
      @Override
      public void onServiceDisconnected(ComponentName name) {

      }
    }, Context.BIND_AUTO_CREATE);

      //计时器
      mhandmhandlele.post(timeRunable);


    GeneratedPluginRegistrant.registerWith(this);
    registerCustomPlugin(this); //注册自定义插件

  }


  private void registerCustomPlugin(PluginRegistry registrar) {
    FlutterTodayStepPlugin.registerWith(registrar.registrarFor(FlutterTodayStepPlugin.CHANNEL));
  }


  /*****************计时器*******************/
    private Runnable timeRunable = new Runnable() {
        @Override
        public void run() {

            currentSecond = currentSecond + 1000;
            if (!isPause) {
                //递归调用本runable对象，实现每隔一秒一次执行任务
                mhandmhandlele.postDelayed(this, 1000);
                if (null != iSportStepInterface) {
                    try {
                        String stepNum = iSportStepInterface.getCurrentTimeSportStep()+"";
                        Log.e("main", "myStep"+stepNum );
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("main", "timeRunable" );

            }
        }
    };
    //计时器
    private Handler mhandmhandlele = new Handler();
    private boolean isPause = false;//是否暂停
    private long currentSecond = 0;//当前毫秒数
/*****************计时器*******************/

}
