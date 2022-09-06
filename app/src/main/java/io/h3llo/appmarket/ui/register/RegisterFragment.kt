package io.h3llo.appmarket.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import io.h3llo.appmarket.R
import io.h3llo.appmarket.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()
    }

    private fun init() {
        // OBTENER LOS GENEROS EN EL VIEWMODEL DE LA CLASE
        viewModel.obtenerGeneros()
    }

    private fun events() = with(binding) {
        btnCrearCuenta.setOnClickListener{
            crearCuenta()
        }
    }

    private fun crearCuenta() = with(binding){
        val nombres = edtNombres.editText?.text.toString()
        val apellidos = edtApellidos.editText?.text.toString()
        val correo = edtCorreoElectronico.editText?.text.toString()
        val celular = edtCelular.editText?.text.toString()
        val numeroDocumento = edtNumeroDocumento.editText?.text.toString()
        val clave = edtClave.editText?.text.toString()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}