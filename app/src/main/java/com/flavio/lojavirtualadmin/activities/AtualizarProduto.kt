package com.flavio.lojavirtualadmin.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.flavio.lojavirtualadmin.R
import com.flavio.lojavirtualadmin.databinding.ActivityAtualizarProdutoBinding
import com.flavio.lojavirtualadmin.datasource.DB

class AtualizarProduto : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarProdutoBinding
    private var fotoProduto: Uri? = null
    private var db = DB()

    private val selecionarFotoGaleria = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if (uri != null){
            fotoProduto = uri
            binding.imgProduto.setImageURI(fotoProduto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

         val codigo = intent.extras!!.getString("codigo")
         val foto = intent.extras!!.getString("foto")
         val nome = intent.extras!!.getString("nome")
         val preco = intent.extras!!.getString("preco")
        Glide.with(this).load(foto).into(binding.imgProduto)

        binding.btSelecionarFotoProduto.setOnClickListener {
            selecionarFotoGaleria.launch("image/*")
        }

        binding.editCodigoProduto.setText(codigo)
        binding.editPrecoProduto.setText(preco)
        binding.editNomeProduto.setText(nome)

        binding.btAtualizar.setOnClickListener {
            val nome = binding.editNomeProduto.text.toString()
            val preco = binding.editPrecoProduto.text.toString()
            val codigo = binding.editCodigoProduto.text.toString()

            if (nome.isEmpty() || preco.isEmpty() || codigo.isEmpty()){
                Toast.makeText(this,"Preencher todos os campos!", Toast.LENGTH_SHORT).show()
            }else if (nome.isNotEmpty() && preco.isNotEmpty() && codigo.isNotEmpty() && fotoProduto != null){
                db.atualizarProdutoComFoto(
                    fotoProduto!!,
                    nome,
                    preco,
                    codigo,
                    this
                )
            }else{
               db.atualizarProdutoSemFoto(
                   nome,
                   preco,
                   codigo,
                   this
               )
            }
        }
    }
}