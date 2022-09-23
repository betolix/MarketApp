package io.h3llo.appmarket.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.h3llo.appmarket.R
import io.h3llo.appmarket.databinding.ItemCategoriesBinding
import io.h3llo.appmarket.model.Categoria

// 1. Definir donde va a estar la data

// 3. Metodos del adaptador: onCreateViewHolder, onBindViewHolder, getItemCount
class CategoriaAdapter constructor( var categories:List<Categoria> = listOf(),
                                    var onClickCategorie : (Categoria) -> Unit ) : RecyclerView.Adapter<CategoriaAdapter.CategoriaAdapterViewHolder>(){

    // UNA VARIABLE DE ESTE TIPO PARA CADE EVENTO
    // lateinit var onClickCategorie : (Categoria) -> Unit

    // 2. Definir una clase interna viewHolder
    // XML (item)
    // Data
    inner class CategoriaAdapterViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding : ItemCategoriesBinding = ItemCategoriesBinding.bind(itemView)

        fun bind(categoria:Categoria){

            Picasso.get().load(categoria.cover).into(binding.imgCategories)

            binding.imgCategories.setOnClickListener{
                // NAVEGAR
                onClickCategorie(categoria)

            }
        }
    }

    fun updateCategories(categories:List<Categoria>){
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaAdapterViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_categories,parent,false)
        return CategoriaAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaAdapterViewHolder, position: Int) {
        val categoria = categories[position]
        holder.bind(categoria)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}