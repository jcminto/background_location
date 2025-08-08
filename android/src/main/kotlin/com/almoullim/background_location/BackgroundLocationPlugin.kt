package com.almoullim.background_location

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

class BackgroundLocationPlugin : FlutterPlugin, ActivityAware {
    
    private var service: BackgroundLocationService? = null
    
    companion object {
        const val TAG = "com.almoullim.Log.Tag"
        const val PLUGIN_ID = "com.almoullim.background_location"
    }

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        service = BackgroundLocationService.getInstance()
        service?.onAttachedToEngine(
            binding.applicationContext, 
            binding.binaryMessenger
        )
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        service?.onDetachedFromEngine()
        service = null
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        service?.let { s ->
            s.setActivity(binding)
            binding.addRequestPermissionsResultListener(s)
        }
    }

    override fun onDetachedFromActivityForConfigChanges() {
        this.onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        this.onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        service?.setActivity(null)
    }
}
