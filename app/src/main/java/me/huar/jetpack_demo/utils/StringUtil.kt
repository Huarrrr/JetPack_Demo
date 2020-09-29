package me.huar.jetpack_demo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern

object StringUtil {
    private var mFormatBuilder = StringBuilder()
    private var mFormatter: Formatter = Formatter(mFormatBuilder, Locale.getDefault())

    //将长度转换为时间
     fun stringForTime(timeMs: Int): String? {
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }

    //将长度转换为秒
    fun stringForSecond(timeMs: Int): String? {
        val totalSeconds = timeMs / 1000
        val hours = totalSeconds / 3600
        val minutes = totalSeconds / 60 % 60
        val seconds = totalSeconds % 60
        mFormatBuilder.setLength(0)
        return mFormatter.format("%d", totalSeconds).toString()
    }

    /**
     * 是否为空
     * @param str 字符串
     * @return true 空 false 非空
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str == ""
    }

    /**
     * 手机号码正则
     */
    fun isMobile(str: String?): Boolean {
//        val p: Pattern?
//        val m: Matcher?
//        var b = false
////        val s2 = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1,2,5-9])|(177))\\\\d{8}\$"
//        val s2 = "^1[3|4|5|7|8][0-9]\\\\d{4,8}\$"
//        if (isEmpty(str)) {
//            p = Pattern.compile(s2)
//            m = p.matcher(str)
//            b = m.matches()
//        }
//        return b

        val regex =
            "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
        return if (str!!.length != 11) {
            false
        } else {
            val p = Pattern.compile(regex)
            val m = p.matcher(str)
            m.matches()
        }
    }

    /**
     * 验证码正则
     */
    fun isCode(str: String?): Boolean {
        val regex = "^\\d{6}\$"
        val p = Pattern.compile(regex)
        val m = p.matcher(str!!)
        return m.matches()
    }

    //以下是获取设备信息  登录时用
    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String? {
        var versionName = ""
        try {
            val packageManager: PackageManager = context.packageManager
            val packInfo: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }

    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): String? {
        var versionCode: String? = ""
        try {
            val packageManager = context.packageManager
            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionCode = java.lang.String.valueOf(packInfo.versionCode)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    fun  getDeviceId(context: Context): String? {
        val androidID: String =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val id = androidID + Build.SERIAL
        return try {
            toMD5(id)
        } catch ( e: NoSuchAlgorithmException) {
            e.printStackTrace()
            id
        }
    }


    //md5加密
    private fun toMD5(sourceStr: String): String? {
        var result = ""
        try {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            md.update(sourceStr.toByteArray())
            val b: ByteArray = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            result = buf.toString().substring(8, 24)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace();
        }
        return result
    }
    /**
     * 获取手机品牌
     *
     * @return
     */
    fun getPhoneBrand(): String? {
        return Build.BRAND
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    fun getPhoneModel(): String? {
        return Build.MODEL
    }


    //渠道
    private const val CHANNEL_KEY = "UMENG_CHANNEL"
    private const val CHANNEL_VERSION_KEY = "cztchannel_version"
    private val mChannel: String? = null

    /**
     * 返回市场。  如果获取失败返回""
     * @param context
     * @return
     */
    fun getChannel(context: Context): String? {
        return getChannel(context, "")
    }

    /**
     * 返回市场。  如果获取失败返回defaultChannel
     * @param context
     * @param defaultChannel
     * @return
     */
    private fun getChannel(
        context: Context,
        defaultChannel: String?
    ): String? {
        try {
            val pm = context.packageManager
            val appInfo =
                pm.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            return appInfo.metaData.getString(CHANNEL_KEY)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        //获取失败
        return defaultChannel
    }


}