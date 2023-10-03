package com.yatish.coinpaprika.data.mapper

import android.content.Context
import com.yatish.coinpaprika.R
import com.yatish.coinpaprika.util.ErrorEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CoinErrorMapper @Inject constructor(@ApplicationContext private val context: Context): Mapper<String, ErrorEntity?> {
    override fun mapToDomainLayer(input: ErrorEntity?): String {
        return getErrorMessage(input)
    }

    private fun getErrorMessage(errorEntity: ErrorEntity?): String {
        return when(errorEntity) {
            ErrorEntity.NotFound -> context.getString(R.string.not_found)
            ErrorEntity.NetWork -> context.getString(R.string.network_error)
            ErrorEntity.TooManyRequests -> context.getString(R.string.network_error)
            else -> context.getString(R.string.un_known)
        }
    }

}