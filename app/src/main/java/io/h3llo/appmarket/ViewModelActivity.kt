package io.h3llo.appmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.h3llo.appmarket.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewModelBinding

    // SUSCRIBIR EL VIEWMODEL
    // MANERA LARGA LINEA 1
    // private lateinit var viewModel: OperacionViewModel
    // MANERA CORTA ( KTX ) LINEA 1
    private val viewModel : OperacionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MANERA LARGA LINEA 2
        // viewModel = ViewModelProvider(this).get(OperacionViewModel::class.java)

        // SUSCRIBIENDO AL VIEWMODEL
        viewModel._number.observe(this, Observer { numero ->
            binding.tvNumero.text = "$numero"
        })

        binding.btnSumar.setOnClickListener{
            val numero = binding.tvNumero.text.toString().toInt() + 1
            viewModel.getNumber(numero)
            // binding.tvNumero.text = "$numero"
        }

        binding.btnRestar.setOnClickListener{
            val numero = binding.tvNumero.text.toString().toInt() - 1
            viewModel.getNumber(numero)
            // binding.tvNumero.text = "$numero"
        }
    }
}