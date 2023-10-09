package com.yatish.domain.mapper

interface Mapper<out O, in I> {
    fun mapToDomainLayer(response: I): O
}