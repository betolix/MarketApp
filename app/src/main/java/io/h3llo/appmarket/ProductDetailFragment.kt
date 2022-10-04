package io.h3llo.appmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.h3llo.appmarket.core.BaseAdapter
import io.h3llo.appmarket.databinding.FragmentProductDetailBinding
import io.h3llo.appmarket.databinding.FragmentProductsBinding
import io.h3llo.appmarket.databinding.ItemImagesBinding
import io.h3llo.appmarket.databinding.ItemProductBinding
import io.h3llo.appmarket.model.Producto


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

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
        setupAdapter()

    }

    private fun setupAdapter() = with(binding) {
        rvDetail.adapter = adapter
    }

    private fun init() = with(binding) {
        arguments?.let { bundle ->
            val product = ProductDetailFragmentArgs.fromBundle(bundle).product

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
