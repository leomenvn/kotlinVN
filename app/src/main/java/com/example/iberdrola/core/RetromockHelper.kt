package com.example.iberdrola.core

import co.infinum.retromock.Retromock
import com.example.iberdrola.data.RetrofitHelper

class RetromockHelper private constructor() {

    companion object {
        @Volatile
        private var instance: Retromock? = null

        fun getIntance(): Retromock {
            return instance ?: synchronized(this) {
                val retrofit = RetrofitHelper.getInstance()
                instance ?: Retromock.Builder()
                    .retrofit(retrofit)
                    .defaultBodyFactory(ResourceBodyFactory())
                    .build().also { instance = it }
            }
        }
    }
}