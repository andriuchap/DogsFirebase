package edu.ktu.dogsfirebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.ktu.dogsfirebase.databinding.FragmentAddDogBinding
import edu.ktu.dogsfirebase.viewmodels.AddDogViewModel

class AddDogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddDogBinding.inflate(inflater, container, false)
        val viewModel: AddDogViewModel by viewModels()
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addBtn.setOnClickListener {
            viewModel.addDog(
                binding.nameTextInput.text.toString(), binding.ageTextInput.text.toString().toInt(),
                binding.breedTextInput.text.toString()
            )
            findNavController().popBackStack()
        }

        return binding.root
    }
}