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

# Remove all logs from Logcat
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

# 1. Debug-friendly
-keepattributes SourceFile,LineNumberTable

# 2. Annotations
-keepattributes *Annotation*
-keepattributes Signature

# 3. Models
-keep class dev.tanakornsss.luminality.data.model.** { *; }

# 4. JSON
-keep class com.google.gson.** { *; }

# 5. DI
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }

# 6. App entry
-keep class dev.tanakornsss.luminality.MainActivity { *; }
