package io.h3llo.appmarket.ui.categories

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import io.h3llo.appmarket.MainActivity
import io.h3llo.appmarket.MainMenuActivity
import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.Message.toast
import io.h3llo.appmarket.core.SecurityPreferences
import io.h3llo.appmarket.core.SecurityPreferences.encryptPreferences
import io.h3llo.appmarket.core.SecurityPreferences.getToken
import io.h3llo.appmarket.databinding.FragmentCategoriesBinding
import io.h3llo.appmarket.databinding.FragmentLoginBinding
import io.h3llo.appmarket.databinding.NoAutorizadoDialogBinding
import io.h3llo.appmarket.util.Constantes


class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    // private lateinit var adaptador: CategoriaAdapter
    private val adaptador by lazy{
        CategoriaAdapter()
    }


    private val viewModel : CategoriaViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        loadData()
        observablesSetup()
    }

    private fun observablesSetup() {
        viewModel.categories.observe(viewLifecycleOwner, Observer{ categorias ->
            adaptador.updateCategories(categorias)
        })

        viewModel.loader.observe(viewLifecycleOwner, Observer { condition ->
            if(condition)binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            requireContext().toast(error)
        })

        viewModel.authorized.observe(viewLifecycleOwner, Observer { message ->
            // LIMPIAR EL TOKEN DE LA PREFERENCIA
            SecurityPreferences.saveToken(
                "",
                requireContext().encryptPreferences(Constantes.PREFERENCES_TOKEN)
            )

            showMessage(message).show()
        })
    }

    private fun loadData() {

        val token = getToken(requireContext().encryptPreferences(Constantes.PREFERENCES_TOKEN))

        viewModel.obtenerCategorias(token)
    }

    private fun setupAdapter() {

    //  adaptador = CategoriaAdapter()
        CategoriaAdapter()
        binding.rvCategories.adapter = adaptador
    }

    private fun showMessage(mensaje:String): AlertDialog {

        val binding = NoAutorizadoDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        val alertDialog = builder.create()

        binding.tvMensaje.text = mensaje

        binding.btnAceptar.setOnClickListener{
            alertDialog.dismiss()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        return alertDialog

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}