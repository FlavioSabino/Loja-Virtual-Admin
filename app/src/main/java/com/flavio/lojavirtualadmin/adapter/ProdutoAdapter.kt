package com.flavio.lojavirtualadmin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavio.lojavirtualadmin.activities.AtualizarProduto
import com.flavio.lojavirtualadmin.databinding.ProdutosItemBinding
import com.flavio.lojavirtualadmin.datasource.DB
import com.flavio.lojavirtualadmin.model.Produto

class ProdutoAdapter(private val context: Context, private val listaProdutos:MutableList<Produto>):
    RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
       val itemLista = ProdutosItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProdutoViewHolder(itemLista)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.txtCodigoProduto.text = "Código: ${listaProdutos[position].codigo}"
        Glide.with(context).load(listaProdutos[position].foto).into(holder.fotoProduto)
        holder.txtNomeProduto.text = listaProdutos[position].nome
        holder.txtPrecoProduto.text = listaProdutos[position].preco

        holder.btAtualizar.setOnClickListener {
            val intent = Intent(context,AtualizarProduto::class.java)
            intent.putExtra("codigo",listaProdutos[position].codigo)
            intent.putExtra("foto",listaProdutos[position].foto)
            intent.putExtra("nome",listaProdutos[position].nome)
            intent.putExtra("preco",listaProdutos[position].preco)
            context.startActivity(intent)
        }

        holder.btDeletar.setOnClickListener {
            val codigo = listaProdutos[position].codigo

            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Excluir Produto")
            alertDialog.setMessage("Deseja excluir esse produto?")
            alertDialog.setPositiveButton("Sim",{_,_ ->
                val db = DB()
                db.deletarProduto(codigo!!)
                listaProdutos.removeAt(position)
                notifyDataSetChanged()
            })
            alertDialog.setNegativeButton("Não",{_,_ ->})
            alertDialog.show()
        }
    }

    override fun getItemCount() = listaProdutos.size


    inner class ProdutoViewHolder(binding: ProdutosItemBinding): RecyclerView.ViewHolder(binding.root){
        val txtCodigoProduto = binding.txtCodigoProduto
        val fotoProduto = binding.fotoProduto
        val txtNomeProduto = binding.txtNomeProduto
        val txtPrecoProduto = binding.txtPrecoProduto
        val btAtualizar = binding.btAtualizar
        val btDeletar = binding.btDeletar

    }
}