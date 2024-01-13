package com.fe.customizekeyboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fe.customizekeyboard.api.models.Keyboard
import com.fe.customizekeyboard.databinding.ViewHolderKeyboardListBinding
import com.squareup.picasso.Picasso
import java.util.Objects

class KeyboardListAdapter: ListAdapter<Keyboard, KeyboardListAdapter.KeyboardListViewHolder> (object :DiffUtil.ItemCallback<Keyboard>(){

    override fun areItemsTheSame(oldItem: Keyboard, newItem: Keyboard): Boolean {
       return oldItem == newItem  ;
    }

    override fun areContentsTheSame(oldItem: Keyboard, newItem: Keyboard): Boolean {
            return oldItem == newItem ;
    }

}){
    class KeyboardListViewHolder(val viewBinding :ViewHolderKeyboardListBinding):ViewHolder(viewBinding.root){

        // bind data from view holder
        fun bind(item:Keyboard){
            Picasso.get().load(item.image_url1).into(viewBinding.imageView1);
            viewBinding.txtProductName.text = item.keyboard_name;
            viewBinding.txtPrice.text = item.price.toString() ;
            viewBinding.txtRate.text = item.rate.toString() ;

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardListViewHolder {
        val inflater = LayoutInflater.from(parent.context) ;
        var binding = ViewHolderKeyboardListBinding.inflate(inflater,parent,false) ;
        return KeyboardListViewHolder(binding) ;
    }

    override fun onBindViewHolder(holder: KeyboardListViewHolder, position: Int) {
       val item = getItem(position);
        holder.bind(item)
    }




}







