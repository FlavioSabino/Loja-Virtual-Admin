package com.flavio.lojavirtualadmin.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.flavio.lojavirtualadmin.R
import com.flavio.lojavirtualadmin.databinding.ActivityCadastroProdutosBinding
import com.flavio.lojavirtualadmin.datasource.DB

class CadastroProdutos : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroProdutosBinding
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
        binding = ActivityCadastroProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSelecionarFotoProduto.setOnClickListener {
            selecionarFotoGaleria.launch("image/*")
        }

        binding.btCadastrar.setOnClickListener {
            val nome = binding.editNomeProduto.text.toString()
            val preco = binding.editPrecoProduto.text.toString()
            val codigo = binding.editCodigoProduto.text.toString()

            if (nome.isEmpty() || preco.isEmpty() || codigo.isEmpty()){
                Toast.makeText(this,"Preencher todos os campos!", Toast.LENGTH_SHORT).show()
            }else{
                db.cadastroProdutos(fotoProduto!!,nome,preco,codigo, this,
                    binding.editNomeProduto,
                    binding.editPrecoProduto,
                    binding.editCodigoProduto)
            }
        }
    }
}