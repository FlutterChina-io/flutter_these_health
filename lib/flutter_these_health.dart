import 'dart:async';

import 'package:flutter/services.dart';

class FlutterTheseHealth {
  static const MethodChannel _channel =
      const MethodChannel('flutter_these_health');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
