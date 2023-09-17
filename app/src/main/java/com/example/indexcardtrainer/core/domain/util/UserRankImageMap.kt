package com.example.indexcardtrainer.core.domain.util

import com.example.indexcardtrainer.R


object UserRankImageMap {
    val map = LinkedHashMap<String, Int>()

    init {
        map[RANK_PRIVATE] = R.drawable.image_rank_private
        map[RANK_PRIVATE_FIRST_CLASS] = R.drawable.image_rank_private_first_class
        map[RANK_SPECIALIST] = R.drawable.image_rank_specialist
        map[RANK_CORPORAL] = R.drawable.image_rank_corporal
        map[RANK_SERGEANT] = R.drawable.image_rank_sergeant
        map[RANK_STAFF_SERGEANT] = R.drawable.image_rank_staff_sergeant
        map[RANK_MASTER_SERGEANT] = R.drawable.image_rank_master_sergeant
        map[RANK_MAJOR] = R.drawable.image_rank_sergeant_major
    }
}