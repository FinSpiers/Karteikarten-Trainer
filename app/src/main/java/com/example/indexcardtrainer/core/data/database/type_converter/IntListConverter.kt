package com.example.indexcardtrainer.core.data.database.type_converter

import androidx.room.TypeConverter


class IntListConverter {
    @TypeConverter
    fun convertIntListToString(indexCards : List<Int>) : String {
        return indexCards.toString()
    }

    @TypeConverter
    fun convertStringToIntList(stringList: String) : List<Int> {
        val result = ArrayList<Int>()
        val split = stringList.replace("[","").replace("]","").replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (_: Exception) {

            }
        }
        return result
    }

}