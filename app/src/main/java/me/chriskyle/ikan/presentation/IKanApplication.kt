package me.chriskyle.ikan.presentation

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.squareup.leakcanary.LeakCanary
import me.chriskyle.ikan.BuildConfig
import me.chriskyle.ikan.presentation.di.component.ApplicationComponent
import me.chriskyle.ikan.presentation.di.component.DaggerApplicationComponent
import me.chriskyle.ikan.presentation.di.module.ApplicationModule
import me.chriskyle.ikan.presentation.di.module.NetworkModule
import me.chriskyle.library.design.tip.Tip
import me.chriskyle.library.social.internal.PlatformConfig
import me.chriskyle.library.toolkit.log.AndroidLogAdapter
import me.chriskyle.library.toolkit.log.FormatStrategy
import me.chriskyle.library.toolkit.log.Logger
import me.chriskyle.library.toolkit.log.PrettyFormatStrategy
import me.chriskyle.library.toolkit.utils.CrashLogger
import java.net.Proxy

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
class IKanApplication : MultiDexApplication() {

    private var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }

        CrashLogger.register(this)

        PlatformConfig.init(this)

        initTip()
        initLogger()
        Fresco.initialize(this)

        FileDownloader
                .setupOnApplicationOnCreate(this)
                .connectionCreator(FileDownloadUrlConnection.Creator(FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000)
                        .readTimeout(15000)
                        .proxy(Proxy.NO_PROXY)
                ))
                .commit()
    }

    var component: ApplicationComponent
        get() {
            if (applicationComponent == null) {
                applicationComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .networkModule(NetworkModule(this))
                        .build()
            }
            return applicationComponent as ApplicationComponent
        }
        set(applicationComponent) {
            this.applicationComponent = applicationComponent
        }

    private fun initTip() {
        Tip.init(this)
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .build()
        Logger.addLogAdapter(LogAdapter(formatStrategy))
    }

    companion object {

        operator fun get(context: Context) = context.applicationContext as IKanApplication

        fun getApplicationContext(): Context? {
            val localObject: Any
            try {
                localObject = (Class.forName("android.app.ActivityThread")
                        .getDeclaredMethod("currentApplication", *arrayOfNulls(0))
                        .invoke(null, *arrayOfNulls(0)) as Application)
                        .applicationContext
                return localObject
            } catch (localThrowable: Throwable) {
                localThrowable.printStackTrace()
            }

            return null
        }
    }

    private class LogAdapter(formatStrategy: FormatStrategy) : AndroidLogAdapter(formatStrategy) {

        override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
    }
}
