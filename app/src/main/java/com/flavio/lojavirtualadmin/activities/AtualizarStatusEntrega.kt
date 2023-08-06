package com.flavio.lojavirtualadmin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.flavio.lojavirtualadmin.R
import com.flavio.lojavirtualadmin.databinding.ActivityAtualizarProdutoBinding
import com.flavio.lojavirtualadmin.databinding.ActivityAtualizarStatusEntregaBinding
import com.flavio.lojavirtualadmin.datasource.DB

class AtualizarStatusEntrega : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarStatusEntregaBinding
    private var status_entrega = "Status de Entrega: Em andamento"
    private var db = DB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarStatusEntregaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pedidoID = intent.extras?.getString("pedidoID").toString()
        val usuarioID = intent.extras?.getString("usuarioID").toString()

        binding.btStatusEntregar1.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked){
                status_entrega = "Status de Entrega: Em trânsito"
            }
        }

        binding.btStatusEntregar2.setOnCheckedChangeListener { button,isChecked  ->
            if (isChecked){
                status_entrega = "Status de Entrega: Entregue"
            }
        }

        binding.btAtualizarStatusEntrega.setOnClickListener {
                db.atualizarStatusPedidoUsuario(
                    status_entrega,
                    usuarioID,
                    pedidoID
                )
                db.atualizarStatusPedidoAdmin(
                    status_entrega,
                    pedidoID,
                    this
                )

        }
    }
}