package com.example.iberdrola.core

import co.infinum.retromock.Retromock

object RetromockHelper {

    fun getRetromock(): Retromock {
        val retrofit = RetrofitHelper.getRetrofit()
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }
}