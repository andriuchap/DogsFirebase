package edu.ktu.dogsfirebase.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.ktu.dogsfirebase.databinding.ItemDogBinding
import edu.ktu.dogsfirebase.models.Dog

class DogAdapter(private val onDeleteClickListener: OnDeleteClickListener) :
    ListAdapter<Dog, DogAdapter.ViewHolder>(DogDiff()) {

    class ViewHolder(val binding: ItemDogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog, onDeleteClickListener: OnDeleteClickListener) {
            binding.dog = dog
            binding.onDeleteClickListener = onDeleteClickListener
        }
    }


    class DogDiff : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onDeleteClickListener)
    }

    class OnDeleteClickListener(val clickListener: (dog: Dog) -> Unit) {
        fun onClick(dog: Dog) = clickListener(dog)
    }

}