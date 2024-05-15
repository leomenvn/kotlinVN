package com.example.iberdrola.ui.facturas.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iberdrola.R
import com.example.iberdrola.databinding.ItemFacturasBinding
import com.example.iberdrola.domain.data.model.Factura
import java.text.SimpleDateFormat
import java.util.Locale

class FacturasListaAdapter(private var facturas: List<Factura>, private val onCLickListener: () -> Unit): RecyclerView.Adapter<FacturasListaAdapter.FacturaViewHolder>() {

    // ViewHolder con cada elemento de un objeto factura que debe mostrar
    class FacturaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFacturasBinding.bind(view)

        fun render(item: Factura, onClickListener: () -> Unit) {
            binding.tvEstadoFactura.text = item.descEstado
            binding.tvMontoFactura.text = formatearMonto(item.importeOrdenacion)
            binding.tvFechaFactura.text = formatearFecha(item.fecha)

            itemView.setOnClickListener {
                onClickListener()
            }

            when (binding.tvEstadoFactura.text) {
                "Pendiente de pago" -> {
                    binding.tvEstadoFactura.setTextColor(Color.RED)
                }
                "Pagada" -> {
                    binding.tvEstadoFactura.setTextColor(Color.GREEN)
                }
                else -> {
                    binding.tvEstadoFactura.setTextColor(Color.BLUE)
                }
            }
        }

        private fun formatearFecha(fecha: String): String? {
            val input = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val output = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            return input.parse(fecha)?.let { output.format(it) }
        }

        private fun formatearMonto(monto: Double): String {
            return String.format("%.2f", monto).replace(".", ",")+"\u20AC"
        }
    }


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


    override fun onBindViewHolder(holder: FacturaViewHolder, position: Int) {
        val factura = facturas[position]
        holder.render(factura, onCLickListener)
    }


    override fun getItemCount() = facturas.size

}


