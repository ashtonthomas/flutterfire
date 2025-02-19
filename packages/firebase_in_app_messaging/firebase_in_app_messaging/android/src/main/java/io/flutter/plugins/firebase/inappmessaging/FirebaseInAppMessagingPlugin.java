// Copyright 2019 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.firebase.inappmessaging;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FirebaseInAppMessagingPlugin */
public class FirebaseInAppMessagingPlugin implements FlutterPlugin, MethodCallHandler {
  private final FirebaseInAppMessaging instance;
  private MethodChannel channel;

  private static MethodChannel setup(BinaryMessenger binaryMessenger) {
    final MethodChannel channel =
        new MethodChannel(binaryMessenger, "plugins.flutter.io/firebase_in_app_messaging");
    channel.setMethodCallHandler(new FirebaseInAppMessagingPlugin());
    return channel;
  }

  public FirebaseInAppMessagingPlugin() {
    instance = FirebaseInAppMessaging.getInstance();
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
    channel = setup(binaryMessenger);
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    if (channel != null) {
      channel.setMethodCallHandler(null);
      channel = null;
    }
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    switch (call.method) {
      case "FirebaseInAppMessaging#triggerEvent":
        {
          String eventName = call.argument("eventName");
          instance.triggerEvent(eventName);
          result.success(null);
          break;
        }
      case "FirebaseInAppMessaging#setMessagesSuppressed":
        {
          Boolean suppress = (Boolean) call.argument("suppress");
          instance.setMessagesSuppressed(suppress);
          result.success(null);
          break;
        }
      case "FirebaseInAppMessaging#setAutomaticDataCollectionEnabled":
        {
          Boolean enabled = (Boolean) call.argument("enabled");
          instance.setAutomaticDataCollectionEnabled(enabled);
          result.success(null);
          break;
        }
      default:
        {
          result.notImplemented();
          break;
        }
    }
  }
}
