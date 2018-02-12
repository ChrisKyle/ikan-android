# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\softWare\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends  android.widget.ImageView
-keep public class * extends android.widget.DatePicker
-keep public class * extends android.widget.ListView
-keep public class * extends android.widget.GridView
-keep public class * extends com.jauker.widget.BadgeView
-keep public class * extends android.widget.BaseAdapter
-keep public class * extends android.app.Dialog
-keep public class * extends android.app.Application

-keep public class * implements java.io.Serializable {*;}


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
     native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
    public void *(android.view.View);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keep public class cn.iautos.cwt.R$*{
    public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# support
-dontwarn android.support.v4.**
-dontwarn android.support.v7.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep interface android.support.v7.app.** { *; }

-dontwarn android.support.**

#友盟相关
-keep class com.umeng.** { *; }
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}

-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.mob.**{ *; }
-keep class com.a.** { *; }
-keep class com.b.** { *; }

-keep class android.support.** { *; }

-keep public class * extends android.app.Fragment

-keepattributes Signature
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# facebook
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
    public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
}

-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings

-keepclassmembers class cn.iautos.cwt.temp.activity.tool.HaoCheDaiActivity {
  public *;
}

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

##umeng push
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn org.apache.thrift.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-assumenosideeffects class android.util.Log {
      public static *** v(...);
      public static *** d(...);
      public static *** i(...);
      public static *** w(...);
}

-dontwarn org.apache.commons.**

-keepattributes *Annotation*
-keepattributes JavascriptInterface

-keep class javax.inject.**{*;}
-dontwarn javax.inject.**
-dontwarn javax.inject.Provider

-keep class android.support.** { *; }
-dontwarn android.support.**
-keep interface android.support.** { *; }
-dontwarn com.google.vending.**
-dontwarn com.android.vending.**

-keep class com.handmark.** { *; }
-dontwarn com.handmark.**
-keep interface com.handmark.**

-keep class javax.inject.Provider
-dontwarn javax.inject.Provider

## retrofit
-dontwarn retrofit2.**
-keep class retrofit2.**{ *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

## org.apache.http
-dontwarn org.apache.http.**
-keep class org.apache.http.**{*;}

-keep class org.json.** {*;}

## android support
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**

## gson
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.** { *; }
-keep interface com.google.** { *; }
-keepattributes com.google.**
-dontwarn com.google.**
-dontwarn com.android.vending.**

## umeng
-keep class com.umeng.** {*; }
-keep class com.umeng.socialize.** {*; }
-dontwarn com.umeng.socialize.**
-dontwarn com.umeng.**
-dontwarn org.apache.commons.**

## tencent
-dontwarn com.tencent.**
-keep class com.tencent.**
-keep interface com.tencent.**
-keep class com.tencent.** {*;}

## google
-keepattributes Signature
-keep class sun.misc.Unsafe.** { *; }
-dontwarn sun.misc.Unsafe.**
-keep class com.google.** { *; }
-keep interface com.google.** { *; }
-keepattributes com.google.**
-keep class com.google.apphosting.** {*;}
-dontwarn com.google.apphosting.**
-dontwarn com.google.**
-dontwarn com.android.vending.**

## okhttp
-keep class okhttp3.** {*;}
-dontwarn okhttp3.**
-keep class okio.** {*;}
-dontwarn okio.**
-keep class com.squareup.** {*;}
-dontwarn com.squareup.**
-keep class com.squareup.okio.** {*;}
-keep class com.squareup.okhttp.** {*;}
-keep class java.nio.** {*;}
-dontwarn java.nio.**

## butterknife
-keep class butterknife.** {*;}
-dontwarn butterknife.**

-ignorewarnings

## glide
-keep class com.bumptech.glide.** {*;}
-dontwarn com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule

 #rxjava
-dontwarn sun.misc.**
-keep class rx.** { *; }
-dontwarn rx.**
-keep class rx.internal.** { *; }
-dontwarn rx.internal.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#rxandroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
