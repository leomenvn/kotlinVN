package com.example.iberdrola.ui.facturas.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iberdrola.R
import com.example.iberdrola.domain.data.model.Factura


class FacturasListaAdapter(private var facturas: List<Factura>) : RecyclerView.Adapter<FacturasListaAdapter.FacturaViewHolder>() {

    // ViewHolder con cada elemento de un objeto factura que debe mostrar
    class FacturaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaCreacionTV: TextView = itemView.findViewById(R.id.tv_fechaFactura)
        val montoTV: TextView = itemView.findViewById(R.id.tv_montoFactura)
        val pendienteTV: TextView = itemView.findViewById(R.id.tv_estadoFactura)
    }

    // Crear nueva vista para las facturas
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_facturas, parent, false)
        return FacturaViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(lista: List<Factura>){
        facturas = lista
        notifyDataSetChanged()
    }

    // Asociar los datos de un objeto específico con la vista correspondiente
    override fun onBindViewHolder(holder: FacturaViewHolder, position: Int) {
        val factura = facturas[position]
        holder.montoTV.text = formatearMonto(factura.importeOrdenacion)
        holder.fechaCreacionTV.text = factura.fecha
        holder.pendienteTV.text = factura.descEstado
    }

    // Formatear fecha
    private fun formatearFecha(fecha: String): String? {
        return null
    }


    // Número de elementos
    override fun getItemCount() = facturas.size


    // Formateo monto
    private fun formatearMonto(monto: Double): String{
        return String.format("%.2f", monto).replace(".", ",")+"\u20AC"
    }
}

