package com.example.iberdrola.domain.data

import com.example.iberdrola.domain.data.database.IberdrolaDatabase
import com.example.iberdrola.domain.data.database.dao.FacturaDAO
import com.example.iberdrola.domain.data.database.entities.FacturaEntity
import com.example.iberdrola.domain.data.model.Factura
import com.example.iberdrola.domain.data.database.network.FacturaService

class FacturaRepository(database: IberdrolaDatabase) {

    private val api = FacturaService()
    private val dao: FacturaDAO = database.getDAOInstance()

    suspend fun getAllFacturasAPI(): List<Factura>? {
        return api.getDataAPI()
    }

    suspend fun isEmpty(): Boolean {
        return dao.getFacturasCount() == 0
    }

    suspend fun getAllFacturasDB(): List<Factura>{
        return entityToModel(dao.getAllFacturas())
    }

    suspend fun insertAllFacturas(facturas:List<Factura>){
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

    suspend fun deleteAllFacturas(){
        dao.deleteAllFacturas()
    }

    suspend fun getByPendiente(estado: String): List<Factura>{
        return entityToModel(dao.getByPendiente(estado))
    }

    suspend fun getByMonto(min: Double, max: Double): List<Factura>{
        return entityToModel(dao.getByMonto(min, max))
    }

    suspend fun getFiltradas(estado: String, min: Double, max: Double): List<Factura>{
        return entityToModel(dao.getFiltradas(estado, min, max))
    }

    private fun entityToModel(entities: List<FacturaEntity>): List<Factura>{
        return entities.map {
            Factura(
                descEstado = it.pendiente,
                importeOrdenacion = it.monto,
                fecha = it.fechaCreacion
            )
        }
    }

    private fun modelToEntity(models: List<Factura>): List<FacturaEntity>{
        return models.map {
            FacturaEntity(
                pendiente = it.descEstado,
                monto = it.importeOrdenacion,
                fechaCreacion = it.fecha
            )
        }
    }
}