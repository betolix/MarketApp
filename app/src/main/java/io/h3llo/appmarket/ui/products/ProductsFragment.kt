package io.h3llo.appmarket.ui.products

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import io.h3llo.appmarket.MainActivity

import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.BaseAdapter
import io.h3llo.appmarket.core.Message.toast
import io.h3llo.appmarket.core.SecurityPreferences
import io.h3llo.appmarket.core.SecurityPreferences.encryptPreferences
import io.h3llo.appmarket.databinding.FragmentProductsBinding
import io.h3llo.appmarket.databinding.ItemProductBinding
import io.h3llo.appmarket.databinding.NoAutorizadoDialogBinding
import io.h3llo.appmarket.model.Categoria
import io.h3llo.appmarket.model.Producto
import io.h3llo.appmarket.util.Constantes


class ProductsFragment : Fragment() {

    private var _binding : FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ProductsViewModel by viewModels()

    private val adapter : BaseAdapter<Producto> = object : BaseAdapter<Producto>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Producto> {
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
            return object : BaseAdapter.BaseViewHolder<Producto>(view){

                private val binding : ItemProductBinding = ItemProductBinding.bind(view)

                override fun bind(entity: Producto) = with(binding) {
                    tvCodigo.text = entity.codigo
                    tvDescripcion.text = entity.descripcion
                    tvPrecio.text = entity.precio.toString()
                    Picasso.get().load(entity.imagenes[0]).error(R.drawable.product_error).into(imgProducto)

                    binding.productContainer.setOnClickListener{
                        onItemSelected(entity)
                    }
                }

            }
        }

    }

    private fun onItemSelected(entity : Producto){
        val directions = ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(entity)
        Navigation.findNavController(binding.root).navigate(directions)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        adapterSetup()
        observablesSetup()
    }

    private fun adapterSetup() {
        binding.rvProducts.adapter = adapter
    }

    private fun observablesSetup() {

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

        viewModel.products.observe(viewLifecycleOwner, Observer{ products ->

            adapter.update(products)

        })
    }

    fun init() {
        // DESTINO -> NOMBRECLASE + ARGS
        arguments?.let{ bundle ->
            val token = SecurityPreferences.getToken(requireContext().encryptPreferences(Constantes.PREFERENCES_TOKEN))
            val uuid = ProductsFragmentArgs.fromBundle(bundle).uuid
            viewModel.getProducts(token, uuid)
        }

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