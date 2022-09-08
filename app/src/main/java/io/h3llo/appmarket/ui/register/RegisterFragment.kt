package io.h3llo.appmarket.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.Message.toast
import io.h3llo.appmarket.databinding.FragmentRegisterBinding
import io.h3llo.appmarket.model.CrearCuentaRequest
import io.h3llo.appmarket.model.Genero

// import com.kaopiz.kprogresshud.KProgressHUD


class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RegisterViewModel by viewModels()

    private var generos : List<Genero> = listOf()
    private var genero= ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()
        observablesSetup()
    }

    private fun observablesSetup() {
        viewModel.loader.observe(viewLifecycleOwner, Observer{ condicion ->
            if(condicion)binding.progressBar2.visibility = View.VISIBLE
            else binding.progressBar2.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{error->
            requireContext().toast(error)
        })

        viewModel.generos.observe(viewLifecycleOwner, Observer{ generos ->
            binding.spGeneros.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,generos)
            this.generos = generos
        })

        viewModel.usuario.observe(viewLifecycleOwner, Observer{ usuario ->
            usuario?.let {
                requireContext().toast("Bienvenido Sr. ${usuario.nombres} ${usuario.apellidos}")
                requireActivity().onBackPressed()// Esto lo envia al login, se podria enviar a la pantalla del menu
            }
        })
    }

    private fun init() {
        // OBTENER LOS GENEROS EN EL VIEWMODEL DE LA CLASE
        viewModel.obtenerGeneros()
    }

    private fun events() = with(binding) {
        btnCrearCuenta.setOnClickListener{
            crearCuenta()
        }

        spGeneros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                genero = generos[p2].genero
                requireContext().toast(genero)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    private fun crearCuenta() = with(binding){
        val nombres = edtNombres.editText?.text.toString()
        val apellidos = edtApellidos.editText?.text.toString()
        val correo = edtCorreoElectronico.editText?.text.toString()
        val celular = edtCelular.editText?.text.toString()
        val numeroDocumento = edtNumeroDocumento.editText?.text.toString()
        val clave = edtClave.editText?.text.toString()

        viewModel.registrarUsuario(CrearCuentaRequest(nombres, apellidos, correo, clave, celular, genero, numeroDocumento))

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}