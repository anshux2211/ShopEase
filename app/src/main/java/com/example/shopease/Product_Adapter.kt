package com.example.shopease

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class Product_Adapter(
    private val lisOfProduct:List<Product>,
    private val context:Context
) :RecyclerView.Adapter<Product_Adapter.ProductViewHolder>(){

    class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val productimg:ImageView=itemView.findViewById(R.id.img_image)
        val productname:TextView=itemView.findViewById(R.id.txt_name)
        val productdetail:TextView=itemView.findViewById(R.id.txt_detail)
        val productprice:TextView=itemView.findViewById(R.id.txt_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(view)

    }

    override fun getItemCount(): Int {
        return lisOfProduct.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val curr_product=lisOfProduct[position]
        holder.productname.text=curr_product.product_name
        holder.productprice.text=curr_product.product_price
        holder.productdetail.text=curr_product.product_detail
        Glide.with(context)
            .load(curr_product.product_image)
            .into(holder.productimg)
    }
}