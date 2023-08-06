package com.flavio.lojavirtualadmin.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import android.widget.Toast
import com.flavio.lojavirtualadmin.activities.TelaPrincipal
import com.flavio.lojavirtualadmin.adapter.PedidoAdapter
import com.flavio.lojavirtualadmin.adapter.ProdutoAdapter
import com.flavio.lojavirtualadmin.model.Pedido
import com.flavio.lojavirtualadmin.model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage

class DB {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun cadastroProdutos(
        fotoProduto: Uri,
        nome: String,
        preco: String,
        codigo: String,
        context: Context,
        editNome: EditText,
        editPreco: EditText,
        editCodigo: EditText
    ){

        val storageReferencia = storage.getReference("/Produtos/${codigo}")
        storageReferencia.putFile(fotoProduto).addOnSuccessListener {
            storageReferencia.downloadUrl.addOnSuccessListener{ uri ->

                var produtoMap = hashMapOf(
                    "foto" to uri.toString(),
                    "nome" to nome,
                    "preco" to preco,
                    "codigo" to codigo
                )

                db.collection("Produtos").document(codigo).set(produtoMap).addOnCompleteListener {
                    Toast.makeText(context,"Sucesso ao cadastrar o produto",Toast.LENGTH_SHORT).show()
                        editNome.setText("")
                        editPreco.setText("")
                        editCodigo.setText("")
                }.addOnFailureListener {

                }
            }
        }

    }

    fun getProdutos(listaProdutos: MutableList<Produto>, produtoAdapter: ProdutoAdapter){
        db.collection("Produtos").get().addOnCompleteListener { documento ->
            if (documento.isSuccessful){
                for (doc in documento.result){
                    val produto = doc.toObject(Produto::class.java)
                    listaProdutos.add(produto)
                    produtoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun atualizarProdutoComFoto(
        fotoProduto: Uri,
        nome: String,
        preco: String,
        codigo: String,
        context: Context
    ){
        val storageReferencia = storage.getReference("/Produtos/${codigo}")
        storageReferencia.putFile(fotoProduto).addOnSuccessListener {
            storageReferencia.downloadUrl.addOnSuccessListener{ uri ->

                db.collection("Produtos").document(codigo).update(
                    "codigo",codigo,
                    "foto", uri.toString(),
                    "nome",nome,
                    "preco",preco
                ).addOnCompleteListener {
                    Toast.makeText(context,"Sucesso ao atualizar o produto",Toast.LENGTH_SHORT).show()
                    val intent =Intent(context,TelaPrincipal::class.java)
                    context.startActivity(intent)
                }.addOnFailureListener {

                }
            }
        }
    }

    fun atualizarProdutoSemFoto(
        nome: String,
        preco: String,
        codigo: String,
        context: Context
    ){
                db.collection("Produtos").document(codigo).update(
                    "codigo",codigo,
                    "nome",nome,
                    "preco",preco
                ).addOnCompleteListener {
                    Toast.makeText(context,"Sucesso ao atualizar o produto",Toast.LENGTH_SHORT).show()
                    val intent =Intent(context,TelaPrincipal::class.java)
                    context.startActivity(intent)
                }.addOnFailureListener {


            }
        }

    fun deletarProduto(codigo: String){
        db.collection("Produtos").document(codigo).delete().addOnCompleteListener {

        }.addOnFailureListener {  }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPedidos(listaPedidos: MutableList<Pedido>, pedidoAdapter: PedidoAdapter){
        db.collection("Pedidos_Admin").get().addOnCompleteListener { documento ->
            if (documento.isSuccessful){
                for (doc in documento.result){
                    val pedido = doc.toObject(Pedido::class.java)
                    listaPedidos.add(pedido)
                    pedidoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun atualizarStatusPedidoUsuario(
        statusEntrega: String,
        usuarioID: String,
        pedidoID: String){
        db.collection("Usuario_Pedidos").document(usuarioID).collection("Pedidos")
            .document(pedidoID).update("status_entrega",statusEntrega).addOnCompleteListener {

            }.addOnFailureListener {  }
    }

    fun atualizarStatusPedidoAdmin(
        statusEntrega: String,
        pedidoID: String,
        context: Context){
        db.collection("Pedidos_Admin").document(pedidoID)
            .update("status_entrega",statusEntrega).addOnCompleteListener {
                val intent = Intent(context,TelaPrincipal::class.java)
                context.startActivity(intent)
            }.addOnFailureListener {  }
    }
    }
