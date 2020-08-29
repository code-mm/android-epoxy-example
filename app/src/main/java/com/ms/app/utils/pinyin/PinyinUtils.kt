package com.bdlbsc.doper.utils.pinyin

import com.github.promeg.pinyinhelper.Pinyin

/**
 * Created by maohuawei on 2018/2/5.
 */
object PinyinUtils {
    /**
     * 汉字拼音
     *
     * @param hanzi
     * @return
     */
    fun hanZi2Pinyin(hanzi: String?): String {
        if (hanzi == null) {
            throw NullPointerException("hanzi null")
        }
        val stringBuilder = StringBuilder()
        val chars = hanzi.toCharArray()
        for (c in chars) {
            if (Character.isWhitespace(c)) {
                continue
            }
            if (c.toInt() > 127) {
                val s = Pinyin.toPinyin(c)
                stringBuilder.append(s)
            } else {
                stringBuilder.append(c)
            }
        }
        return stringBuilder.toString()
    }
}