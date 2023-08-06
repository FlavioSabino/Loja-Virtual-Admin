package com.flavio.lojavirtualadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.flavio.lojavirtualadmin.activities.TelaPrincipal
import com.flavio.lojavirtualadmin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                Toast.makeText(this,"Preencha todos os campos!",Toast.LENGTH_SHORT).show()
            }else if (email.equals("admin@gmail.com")){
               autenticacaoAdmin(email,senha)
            }
        }
    }

    private fun autenticacaoAdmin(email: String, senha: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener { autenticacao ->
            if (autenticacao.isSuccessful){
                 val intent = Intent(this, TelaPrincipal::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Erro ao fazer o login!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            val intent = Intent(this, TelaPrincipal::class.java)
            startActivity(intent)
            finish()
        }
    }

}