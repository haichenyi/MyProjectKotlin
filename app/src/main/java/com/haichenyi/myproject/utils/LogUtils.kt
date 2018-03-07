package com.haichenyi.myproject.utils

import android.util.Log
import com.haichenyi.myproject.BuildConfig
import java.util.*

/**
 * Author: 海晨忆
 * Date: 2018/2/26
 * Desc:
 */
object LogUtils {
    private const val V = Log.VERBOSE
    private const val D = Log.DEBUG
    private const val I = Log.INFO
    private const val W = Log.WARN
    private const val E = Log.ERROR
    private const val A = Log.ASSERT
    private var sConsoleFilter = V

    private const val MAX_LEN = 4000

    private var sTagIsSpace = true // log标签是否为空白
    private var sLogHeadSwitch = true // log头部开关，默认开
    private var sLogBorderSwitch = true // log边框开关，默认开
    private var sLog2FileSwitch = false// log写入文件开关，默认关
    private var sLogSwitch = true // log总开关，默认开
    private var sLog2ConsoleSwitch = true // logcat是否打印，默认打印

    private var sGlobalTag: String = "log" // log标签

    private const val ARGS = "args"
    private val LINE_SEP = System.getProperty("line.separator")

    private const val TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════"
    private const val LEFT_BORDER = "║ "
    private const val BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════"

    fun v(content: String) {
        log(V, sGlobalTag, content)
    }

    fun v(tag: String, vararg content: String) {
        log(V, tag, *content)
    }

    fun d(content: String) {
        log(D, sGlobalTag, content)
    }

    fun d(tag: String, vararg content: String) {
        log(D, tag, *content)
    }

    fun i(content: String) {
        log(I, sGlobalTag, content)
    }

    fun i(tag: String, vararg content: String) {
        log(I, tag, *content)
    }

    fun w(content: String) {
        log(W, sGlobalTag, content)
    }

    fun w(tag: String, vararg content: String) {
        log(W, tag, *content)
    }

    fun e(content: String) {
        log(E, sGlobalTag, content)
    }

    fun e(tag: String, vararg content: String) {
        log(E, tag, *content)
    }

    fun a(content: String) {
        log(A, sGlobalTag, content)
    }

    fun a(tag: String, vararg content: String) {
        log(A, tag, *content)
    }

    private fun log(type: Int, tag: String, vararg content: String) {
        if (!BuildConfig.DEBUG) {
            return
        }
        if (!sLogSwitch || !sLog2ConsoleSwitch && !sLog2FileSwitch) {
            return
        }
        val typeLow = type and 0x0F
        if (typeLow < sConsoleFilter) {
            return
        }
        val tagAndHead = processTagAndHead(tag)
        val body = processBody(*content)
        if (sLog2ConsoleSwitch && typeLow >= sConsoleFilter) {
            print2Console(typeLow, tagAndHead[0], tagAndHead[1] + body)
        }
    }

    private fun processTagAndHead(tags: String?): Array<String> {
        var tag = tags
        if (!sTagIsSpace && !sLogHeadSwitch) {
            tag = sGlobalTag
        } else {
            val targetElement = Throwable().stackTrace[3]
            var className = targetElement.className
            val classNameInfo = className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (classNameInfo.isNotEmpty()) {
                className = classNameInfo[classNameInfo.size - 1]
            }
            if (className.contains("$")) {
                className = className.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            }
            if (sTagIsSpace) {
                tag = if (isSpace(tag)) className else tag
            }
            if (sLogHeadSwitch) {
                val head = Formatter().format("%s(%s.java:%d)", targetElement.methodName,
                        className, targetElement.lineNumber).toString()
                return arrayOf(requireNotNull(tag), head + LINE_SEP, " [$head]: ")
            }
        }
        return arrayOf(requireNotNull(tag), "", ": ")
    }

    private fun isSpace(tag: String?) = if (null == tag) true else (0 until tag.length).all { Character.isWhitespace(tag[it]) }

    private fun processBody(vararg contents: String) = if (contents.size == 1) {
        val content = contents[0]
        content
    } else {
        val sb = StringBuilder()
        for (i in contents.indices) {
            val obj = contents[i]
            sb.append(ARGS).append("[").append(i).append("]").append(" = ")
                    .append(obj).append(LINE_SEP)
        }
        sb.toString()
    }

    private fun print2Console(type: Int, tag: String, message: String) {
        var msg = message
        if (sLogBorderSwitch) {
            print(type, tag, TOP_BORDER)
            msg = addLeftBorder(msg)
        }
        val len = msg.length
        val countOfSub = len / MAX_LEN
        if (countOfSub > 0) {
            print(type, tag, msg.substring(0, MAX_LEN))
            var sub: String
            var index = MAX_LEN
            for (i in 0 until countOfSub) {
                sub = msg.substring(index, Math.min(msg.length, index + MAX_LEN))
                print(type, tag, if (sLogBorderSwitch) LEFT_BORDER + sub else sub)
                if (index + MAX_LEN > msg.length) {
                    break
                }
                index += MAX_LEN
            }
            sub = msg.substring(index, len)
            print(type, tag, if (sLogBorderSwitch) LEFT_BORDER + sub else sub)
        } else {
            print(type, tag, msg)
        }
        if (sLogBorderSwitch) {
            print(type, tag, BOTTOM_BORDER)
        }
    }

    private fun addLeftBorder(msg: String) = if (!sLogBorderSwitch) msg else {
        val sb = StringBuilder()
        val lines = msg.split(LINE_SEP.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            sb.append(LEFT_BORDER).append(line).append(LINE_SEP)
        }
        sb.toString()
    }

    private fun print(type: Int, tag: String, msg: String) {
        Log.println(type, tag, msg)
    }
}