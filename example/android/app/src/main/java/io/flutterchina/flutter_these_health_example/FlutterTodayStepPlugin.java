package io.flutterchina.flutter_these_health_example;

import android.app.Activity;
import android.os.RemoteException;
import android.util.Log;

import com.today.step.lib.ISportStepInterface;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FlutterTodayStepPlugin implements MethodChannel.MethodCallHandler{
    private ISportStepInterface iSportStepInterface;
    private Integer mStepSum = 0;
    public static String CHANNEL = "com.today.step";

    static MethodChannel channel;

    private Activity activity;

    private FlutterTodayStepPlugin(Activity activity) {
        this.activity = activity;
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        channel = new MethodChannel(registrar.messenger(), CHANNEL);
        FlutterTodayStepPlugin instance = new FlutterTodayStepPlugin(registrar.activity());
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals("myStep")) {
            iSportStepInterface = MainActivity.getISportStepInterface();
            //获取今日步数
            if (null != iSportStepInterface) {
                try {
                     mStepSum = iSportStepInterface.getCurrentTimeSportStep();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            result.success(mStepSum);
        }
        else {
            result.notImplemented();
        }
    }
}
