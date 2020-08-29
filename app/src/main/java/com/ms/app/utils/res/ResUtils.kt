package com.bdlbsc.doper.utils.res

import com.ms.app.app.App


class ResUtils {
    fun getLayoutId(paramString: String?): Int {
        return App.INSTANCE!!.resources.getIdentifier(
                paramString, "layout",
                App.INSTANCE!!.packageName
        )
    }

    fun getStringId(paramString: String?): Int {
        return App.INSTANCE!!.resources.getIdentifier(
                paramString, "string",
                App.INSTANCE!!.packageName
        )
    }

    fun getDrawableId(paramString: String?): Int {
        return App.INSTANCE!!.resources.getIdentifier(
                paramString,
                "drawable", App.INSTANCE!!.packageName
        )
    }

    fun getStyleId(paramString: String?): Int {
        return App.INSTANCE!!.resources.getIdentifier(
                paramString,
                "style", App.INSTANCE!!.packageName
        )
    }

    fun getId(paramString: String?): Int {
        return App.INSTANCE!!.resources
                .getIdentifier(paramString, "id", App.INSTANCE!!.packageName)
    }

    fun getColorId(paramString: String?): Int {
        return App.INSTANCE!!.resources.getIdentifier(
                paramString,
                "color", App.INSTANCE!!.packageName
        )
    }

    fun getArrayId(paramString: String?): Int {
        return App.INSTANCE!!.resources.getIdentifier(
                paramString,
                "array", App.INSTANCE!!.packageName
        )
    }

    fun getString(id: Int): String {
        return App.INSTANCE!!.getString(id)
    }

    companion object {
        val instance = ResUtils()
    }
}