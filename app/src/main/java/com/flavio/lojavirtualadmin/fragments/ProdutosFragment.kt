package com.flavio.lojavirtualadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.flavio.lojavirtualadmin.R
import com.flavio.lojavirtualadmin.adapter.ProdutoAdapter
import com.flavio.lojavirtualadmin.databinding.FragmentProdutosBinding
import com.flavio.lojavirtualadmin.datasource.DB
import com.flavio.lojavirtualadmin.model.Produto


class ProdutosFragment : Fragment() {

    private lateinit var binding: FragmentProdutosBinding
    private lateinit var produtoAdapter: ProdutoAdapter
    private val listaProduto: MutableList<Produto> = mutableListOf()
    private val db = DB()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProdutosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewProdutos = binding.recyclerViewProdutos
        recyclerViewProdutos.layoutManager = LinearLayoutManager(context)
        produtoAdapter = ProdutoAdapter(requireContext(),listaProduto)
        recyclerViewProdutos.setHasFixedSize(true)
        recyclerViewProdutos.adapter = produtoAdapter
        db.getProdutos(listaProduto,produtoAdapter)
    }


}