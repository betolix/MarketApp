package io.h3llo.appmarket.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import io.h3llo.appmarket.MainMenuActivity
import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.BaseAdapter
import io.h3llo.appmarket.core.Message.toast
import io.h3llo.appmarket.core.SecurityPreferences
import io.h3llo.appmarket.core.SecurityPreferences.encryptPreferences
import io.h3llo.appmarket.databinding.EditProductDialogBinding
import io.h3llo.appmarket.databinding.FragmentLoginBinding
import io.h3llo.appmarket.databinding.FragmentMisComprasBinding
import io.h3llo.appmarket.databinding.ItemCartBinding
import io.h3llo.appmarket.databinding.ItemProductBinding
import io.h3llo.appmarket.model.Carrito
import io.h3llo.appmarket.model.Producto
import io.h3llo.appmarket.ui.login.LoginViewModel
import io.h3llo.appmarket.util.Constantes


class MisComprasFragment : Fragment() {


    private var _binding: FragmentMisComprasBinding? = null
    private val binding get() = _binding!!

    private val viewModel : CartViewModel by viewModels()


    private val adapter : BaseAdapter<Carrito> = object : BaseAdapter<Carrito>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Carrito> {
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
            return object : BaseAdapter.BaseViewHolder<Carrito>(view){

                private val binding : ItemCartBinding = ItemCartBinding.bind(view)

                override fun bind(entity: Carrito) = with(binding) {
                    tvDescription.text = entity.descripcion
                    tvCant.text = entity.cantidad.toString()
                    tvTotal.text = (entity.precio * entity.cantidad).toString()

                    Picasso.get().load(entity.imagen).error(R.drawable.product_error).into(imgCart)

                    imgEdit.setOnClickListener{
                        // Toast.makeText(requireContext(),entity.descripcion,Toast.LENGTH_SHORT).show()
                        createShowDialogUpdateProduct(entity).show()

                    }

                    imgDelete.setOnClickListener{
                        //Toast.makeText(requireContext(),entity.descripcion,Toast.LENGTH_SHORT).show()
                        deleteProduct(entity)

                    }
                }
            }
        }
    }

    private fun createShowDialogUpdateProduct(entity: Carrito) : AlertDialog {
        val binding = EditProductDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        val alertDialog = builder.create()

        binding.tvHeaderProduct.text = entity.descripcion
        binding.tvCantDialog.text = entity.cantidad.toString()
        Picasso.get().load(entity.imagen).into(binding.imgDialog)

        val total = (entity.precio) * (entity.cantidad)
        val format = "%.2f".format(total)
        binding.tvTotalDialog.text = "$format"

        binding.imgPlusDialog.setOnClickListener{

            val cantidad = binding.tvCantDialog.text.toString().toInt() + 1
            binding.tvCantDialog.text = "$cantidad"

            entity.cantidad = cantidad

            var total = 0.0
            total+=(entity.precio) * cantidad

            val format = "%.2f".format(total)
            binding.tvTotalDialog.text = format
        }

        binding.imgLessDialog.setOnClickListener{

            if(binding.tvCantDialog.text.toString().toInt() > 0) {

                val cantidad = binding.tvCantDialog.text.toString().toInt() - 1
                binding.tvCantDialog.text = "$cantidad"

                entity.cantidad = cantidad

                var total = 0.0
                total+=(entity.precio) * cantidad

                val format = "%.2f".format(total)
                binding.tvTotalDialog.text = format

            }
        }

        binding.btnUpdate.setOnClickListener{
            viewModel.updateCart(entity)
            alertDialog.dismiss()

        }

        return alertDialog

    }

    private fun deleteProduct(entity:Carrito) {

        viewModel.delete(entity)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMisComprasBinding.inflate(inflater, container, false )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        adapterSetup()
        observablesSetup()
    }

    private fun adapterSetup() = with(binding) {

        rvCart.adapter = adapter
    }

    private fun observablesSetup() {
        viewModel.loader.observe(viewLifecycleOwner, Observer { condition ->
            if(condition)binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{ error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        viewModel.cart.observe(viewLifecycleOwner, Observer { cart ->
            adapter.update(cart)
        })

        viewModel.success.observe(viewLifecycleOwner, Observer { messageSuccess ->
            requireContext().toast(messageSuccess)
        })



    }

    private fun init() = with(binding) {
        viewModel.getCart?.observe(viewLifecycleOwner, Observer{ cart ->

            var total = 0.0

            /*
            for(product in cart){
                total += (product.precio) * (product.cantidad)
            }*/

            cart.forEach { product ->
                total += (product.precio) * (product.cantidad)
            }
            tvTotal.text = "$total"
            adapter.update(cart)

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}