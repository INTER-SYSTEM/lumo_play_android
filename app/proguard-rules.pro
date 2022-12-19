# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# RxAndroidBle
-keepclassmembers class * extends android.bluetooth.BluetoothGattCallback {
    # This method is hidden in AOSP sources and therefore Proguard strips it by default
    public void onConnectionUpdated(android.bluetooth.BluetoothGatt, int, int, int, int);
}
-keep class com.polidea.rxandroidble3.** { *; }
-dontwarn com.polidea.rxandroidble3.*