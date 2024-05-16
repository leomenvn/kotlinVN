package com.example.iberdrola

import com.example.iberdrola.domain.data.database.FacturaDAO
import com.example.iberdrola.domain.data.database.FacturaEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FiltradoUnitTest {

    private val dao = mock(FacturaDAO::class.java)
    private val fact = mock(FacturaEntity::class.java)
    private val lista = listOf(fact)

    @Before
    fun setup(){
    }

    @Test
    fun `al insertar factura no esta vacia`() = runBlocking{
        `when`(dao.getAllFacturas()).thenReturn(lista)
        dao.insertAllFacturas(lista)
        val result = dao.getAllFacturas()

        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `al borrar las facturas la BDD esta vacia`() = runBlocking{

        `when`(dao.getAllFacturas()).thenReturn(emptyList())
        dao.insertAllFacturas(lista)
        dao.deleteAllFacturas()
        val result = dao.getAllFacturas()

        assertTrue(result.isEmpty())
    }
}