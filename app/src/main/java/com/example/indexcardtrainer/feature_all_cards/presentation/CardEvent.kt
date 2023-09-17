package com.example.indexcardtrainer.feature_all_cards.presentation

import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.feature_all_cards.domain.SortingType

sealed class CardEvent {

    class CardCreation(val value : String, val solution : String, val category : String?) : CardEvent()

    class CardDeletion(val card : IndexCard) : CardEvent()

    class CardEditing(val card: IndexCard) : CardEvent()

    class ChangeSortingType(val sortingType: SortingType) : CardEvent()

    class CardDetails(val card : IndexCard) : CardEvent()
}
