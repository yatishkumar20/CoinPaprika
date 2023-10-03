package com.yatish.coinpaprika.data.mapper

interface Mapper<out O, in I> {
    fun mapToDomainLayer(input: I): O
}