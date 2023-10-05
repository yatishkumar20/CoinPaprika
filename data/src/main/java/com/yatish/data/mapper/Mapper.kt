package com.yatish.data.mapper

interface Mapper<out O, in I> {
    fun mapToDomainLayer(input: I): O
}