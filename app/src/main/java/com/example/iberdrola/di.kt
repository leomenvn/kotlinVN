package com.example.iberdrola

import androidx.room.Room
import co.infinum.retromock.Retromock
import com.example.iberdrola.core.RemoteConfigHelper
import com.example.iberdrola.core.ResourceBodyFactory
import com.example.iberdrola.data_ktor.KtorHelper
import com.example.iberdrola.data_retrofit.FacturaService
import com.example.iberdrola.domain.data.FacturaRepository
import com.example.iberdrola.domain.data.database.FacturaDAO
import com.example.iberdrola.domain.data.database.IberdrolaDatabase
import com.example.iberdrola.ui.MainActivityViewModel
import com.example.iberdrola.ui.auth.AuthViewModel
import com.example.iberdrola.ui.facturas.FacturasViewModel
import com.example.iberdrola.ui.smartsolar.fragments.DetallesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { MyApplication() }
    single { RemoteConfigHelper(get()) }
    single { KtorHelper() }
    single {
        Retrofit.Builder()
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single{
        Retromock.Builder()
            .retrofit(get())
            .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }
    single{ FacturaService(get(), get()) }
    single {
        Room.databaseBuilder(
            get(),
            IberdrolaDatabase::class.java, "IberdrolaDatabase"
        ).build()
    }
    single<FacturaDAO> {
        val database = get<IberdrolaDatabase>()
        database.getDAOInstance()
    }
    single{ FacturaRepository(get(), get()) }


    viewModel { AuthViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { FacturasViewModel(get(), get(), get()) }
    viewModel { DetallesFragmentViewModel(get()) }

}