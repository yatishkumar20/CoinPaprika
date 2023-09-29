package com.yatish.coinpaprika.data.mapper

interface Mapper<out O, in I> {
    fun mapToDomainLayerModel(input: I): O
}