package com.flavio.lojavirtualadmin.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.flavio.lojavirtualadmin.R
import com.flavio.lojavirtualadmin.adapter.PedidoAdapter
import com.flavio.lojavirtualadmin.databinding.FragmentPedidosBinding
import com.flavio.lojavirtualadmin.datasource.DB
import com.flavio.lojavirtualadmin.model.Pedido


class PedidosFragment : Fragment() {

    private lateinit var binding: FragmentPedidosBinding
    private lateinit var pedidoAdapter: PedidoAdapter
    private val listaPedidos: MutableList<Pedido> = mutableListOf()
    private val db = DB()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPedidosBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewPedidos = binding.recyclerViewPedidos
        recyclerViewPedidos.layoutManager = LinearLayoutManager(context)
        pedidoAdapter = PedidoAdapter(requireContext(),listaPedidos)
        recyclerViewPedidos.setHasFixedSize(true)
        recyclerViewPedidos.adapter = pedidoAdapter
        db.getPedidos(listaPedidos,pedidoAdapter)
    }

}