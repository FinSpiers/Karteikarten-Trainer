package com.example.indexcardtrainer.core.data.database.type_converter

import androidx.room.TypeConverter
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.google.gson.Gson
import com.google.gson.reflect.*


class IndexCardListConverter {
    @TypeConverter
    fun convertIndexCardListToString(indexCards : List<IndexCard>) : String {
        return Gson().toJson(indexCards)
    }

    @TypeConverter
    fun convertStringToIndexCardList(string: String) : List<IndexCard> {
        val typeToken = TypeToken.getParameterized(List::class.java, IndexCard::class.java)
        return Gson().fromJson(string, typeToken.type)
    }

}