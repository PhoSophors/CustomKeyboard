package com.fe.customizekeyboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fe.customizekeyboard.api.models.Keyboard
import com.fe.customizekeyboard.databinding.FragmentProductDetailBinding
import com.fe.customizekeyboard.databinding.ViewHolderKeyboardDetailBinding
import com.squareup.picasso.Picasso

class KeyboardDetailAdapter: ListAdapter<Keyboard, KeyboardDetailAdapter.KeyboardDetailViewHolder> (object :DiffUtil.ItemCallback<Keyboard>(){

    override fun areItemsTheSame(oldItem: Keyboard, newItem: Keyboard): Boolean {
       return oldItem == newItem  ;
    }

    override fun areContentsTheSame(oldItem: Keyboard, newItem: Keyboard): Boolean {
            return oldItem == newItem ;
    }

}){
    class KeyboardDetailViewHolder(val viewBinding: FragmentProductDetailBinding):ViewHolder(viewBinding.root){

        // bind data from view holder
//        fun bind(item:Keyboard){
//            Picasso.get().load(item.image_url1).into(viewBinding.image1);
//            Picasso.get().load(item.image_url2).into(viewBinding.image2);
//            viewBinding.textkeyboardName.text = item.keyboard_name ;
//            viewBinding.textPrice.text = item.price.toString() ;
//            viewBinding.txtRate.text = item.rate.toString();
//
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardDetailViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: KeyboardDetailViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardDetailViewHolder {
////        val inflater = LayoutInflater.from(parent.context) ;
////        var binding = ViewHolderKeyboardDetailBinding.inflate(inflater,parent,false)
////        return KeyboardDetailViewHolder(binding) ;
//    }
//
//    override fun onBindViewHolder(holder: KeyboardDetailViewHolder, position: Int) {
//        val item = getItem(position);
//        holder.bind(item)
//    }


}







