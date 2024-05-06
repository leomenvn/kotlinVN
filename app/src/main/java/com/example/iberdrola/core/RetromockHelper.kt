package com.example.iberdrola.core

import co.infinum.retromock.Retromock
import com.example.iberdrola.data.RetrofitHelper

object RetromockHelper {

    fun getRetromock(): Retromock {
        val retrofit = RetrofitHelper.getRetrofit()
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }
}