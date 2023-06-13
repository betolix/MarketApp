package io.h3llo.appmarket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.BaseAdapter
import io.h3llo.appmarket.core.Message.toast
import io.h3llo.appmarket.core.SecurityPreferences
import io.h3llo.appmarket.core.SecurityPreferences.encryptPreferences
import io.h3llo.appmarket.databinding.FragmentProductDetailBinding
import io.h3llo.appmarket.databinding.ItemImagesBinding
import io.h3llo.appmarket.model.Carrito
import io.h3llo.appmarket.model.Producto
import io.h3llo.appmarket.util.Constantes


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    // SUSCRIBIMOS EL FRAGMENTO AL VIEWMODEL
    private val viewModel : DetailViewModel by viewModels()

    private lateinit var product: Producto

    private var uuidCategory = ""

    private val adapter: BaseAdapter<String> = object : BaseAdapter<String>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseViewHolder<String> {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
            return object : BaseAdapter.BaseViewHolder<String>(view) {

                private val binding: ItemImagesBinding = ItemImagesBinding.bind(view)

                override fun bind(entity: String) = with(binding) {

                    Picasso.get().load(entity).error(R.drawable.product_error).into(imgSecondary)

                }
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()
        setupAdapter()
        observablesSetup()

    }

    private fun observablesSetup() = with(binding) {
        viewModel.loader.observe(viewLifecycleOwner, Observer { condition ->
            if(condition)binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            requireContext().toast(error)
        })


        viewModel.responseSuccess.observe(viewLifecycleOwner, Observer{ success ->
            requireContext().toast(success)
            Navigation.findNavController(binding.root).navigate(R.id.action_productDetailFragment_to_categoriesFragment)
        })
    }

    private fun events() = with(binding) {
        imgAdd.setOnClickListener{
            val number = tvCant.text.toString().toInt() + 1
            tvCant.text = "$number"

        }
        imgLess.setOnClickListener {
            if(tvCant.text.toString().toInt() != 0){
                val number = tvCant.text.toString().toInt() + 1
                tvCant.text = "$number"
            }
        }

        btnAdd.setOnClickListener {

            val cart = Carrito(
                product.uuid,
                product.descripcion,
                product.precio,
                tvCant.text.toString().toInt(),
                product.imagenes[0] ?: "",
                uuidCategory
            )
            // TODO SAVE TO LOCAL DB - ROOM
            viewModel.addToCart(cart)
        }

    }

    private fun setupAdapter() = with(binding) {
        rvDetail.adapter = adapter
    }

    private fun init() = with(binding) {
        arguments?.let { bundle ->
            product = ProductDetailFragmentArgs.fromBundle(bundle).product
             uuidCategory = ProductDetailFragmentArgs.fromBundle(bundle).uuidCategory

            product?.let { product ->
                tvDescription.text = product.descripcion
                tvPrice.text = product.precio.toString()
                tvDetail.text = product.caracteristicas

                Picasso.get().load(product.imagenes[0]).error(R.drawable.product_error).into(imgDetail)

                adapter.update(product.imagenes.filter { url ->
                    url != product.imagenes[0]
                })
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
