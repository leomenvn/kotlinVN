package com.example.iberdrola.domain.data

import com.example.iberdrola.data_ktor.KtorHelper
import com.example.iberdrola.domain.data.database.IberdrolaDatabase
import com.example.iberdrola.domain.data.database.FacturaDAO
import com.example.iberdrola.domain.data.database.FacturaEntity
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.data_retrofit.FacturaService
import com.example.iberdrola.data_retrofit.response.DetallesResponse

class FacturaRepository (
    database: IberdrolaDatabase,
    private val api: FacturaService,
                        ) {

    private val ktor = KtorHelper()
    private val dao: FacturaDAO = database.getDAOInstance()

    suspend fun getAllFacturasAPI(mode: Boolean): List<Factura>? {
        return api.getDataAPI(mode)
    }

    suspend fun getAllFacturasKtor(): List<Factura> {
        return ktor.getData()
    }

    suspend fun getDetalles(): DetallesResponse? {
        return api.getDetallesAPI()
    }


    suspend fun getAllFacturasDB(): List<Factura>{
        return entityToModel(dao.getAllFacturas())
    }

    suspend fun getMayorMonto(): Double{
        return dao.getMayorMonto()
    }

    suspend fun insertAllFacturas(facturas:List<Factura>){
        dao.deleteAllFacturas()
        dao.insertAllFacturas(modelToEntity(facturas))
    }

    suspend fun deleteFactura(factura: Factura){
        val fact = FacturaEntity(
            pendiente = factura.descEstado,
            monto = factura.importeOrdenacion,
            fechaCreacion = factura.fecha
        )
        dao.delete(fact)
    }


    suspend fun getFiltradas(estado: HashMap<String, Boolean>, monto: Double, fechaMin: String, fechaMax: String): List<Factura>{
        var b = false
        val aux = mutableListOf<String>()
        estado.forEach { (key, value) ->
            if (value) {
                aux.add(key)
                b = true
            } else {
                aux.add("''")
            }
        }
        return if(b){
            entityToModel(dao.getAllFiltradas(aux[0], aux[1], aux[2], aux[3], aux[4], monto, fechaMin, fechaMax))
        }else{
            entityToModel(dao.getFiltradas(monto, fechaMin, fechaMax))
        }
    }


    private fun entityToModel(entities: List<FacturaEntity>): List<Factura>{
        return entities.map {
            Factura(
                descEstado = it.pendiente,
                importeOrdenacion = it.monto,
                fecha = fechaModel(it.fechaCreacion)
            )
        }
    }


    private fun modelToEntity(models: List<Factura>): List<FacturaEntity>{
        return models.map {
            FacturaEntity(
                pendiente = it.descEstado,
                monto = it.importeOrdenacion,
                fechaCreacion = fechaEntity(it.fecha)
            )
        }
    }


    private fun fechaModel(fecha: String): String {
        val aux = fecha.split("-")
        val dd = aux[2]
        val mm = aux[1]
        val yy = aux[0]
        return "$dd-$mm-$yy"
    }


    private fun fechaEntity(fecha: String): String{
        val aux = fecha.split("/")
        val dd = aux[0]
        val mm = aux[1]
        val yy = aux[2]
        return "$yy-$mm-$dd"
    }

}