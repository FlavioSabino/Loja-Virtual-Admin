package com.flavio.lojavirtualadmin.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flavio.lojavirtualadmin.MainActivity
import com.flavio.lojavirtualadmin.R
import com.flavio.lojavirtualadmin.databinding.ActivityTelaPrincipalBinding
import com.flavio.lojavirtualadmin.fragments.PedidosFragment
import com.flavio.lojavirtualadmin.fragments.ProdutosFragment
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentRender(R.id.containerFragmentProdutos,ProdutosFragment())

        binding.btCadastroProdutos.setOnClickListener {
            val intent = Intent(this,CadastroProdutos::class.java)
            startActivity(intent)
        }

        binding.btProdutos.setOnClickListener {
            clicked = true

            if (clicked){
                binding.btProdutos.setBackgroundResource(R.drawable.bg_button_enabled)
                binding.btProdutos.setTextColor(Color.WHITE)
                binding.btPedidos.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btPedidos.setTextColor(Color.BLACK)

                binding.containerFragmentPedidos.visibility = View.INVISIBLE
                binding.containerFragmentProdutos.visibility = View.VISIBLE
                fragmentRender(R.id.containerFragmentProdutos,ProdutosFragment())
            }
        }

        binding.btPedidos.setOnClickListener {
            clicked = true

            if (clicked){
                binding.btPedidos.setBackgroundResource(R.drawable.bg_button_enabled)
                binding.btPedidos.setTextColor(Color.WHITE)
                binding.btProdutos.setBackgroundResource(R.drawable.bg_button_disabled)
                binding.btProdutos.setTextColor(Color.BLACK)

                binding.containerFragmentProdutos.visibility = View.INVISIBLE
                binding.containerFragmentPedidos.visibility = View.VISIBLE
                fragmentRender(R.id.containerFragmentPedidos,PedidosFragment())
            }
        }

        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fragmentRender(containerId: Int, fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerId,fragment)
        fragmentTransaction.commit()
    }
}