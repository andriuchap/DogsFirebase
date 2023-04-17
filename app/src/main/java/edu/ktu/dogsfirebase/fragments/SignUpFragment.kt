package edu.ktu.dogsfirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.ktu.dogsfirebase.R
import edu.ktu.dogsfirebase.databinding.FragmentSignUpBinding
import edu.ktu.dogsfirebase.viewmodels.SignUpViewModel

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val viewModel: SignUpViewModel by viewModels()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToDogListFragment())
            }
        }

        binding.signUpBtn.setOnClickListener {
            viewModel.signUp(
                binding.emailTextInput.text.toString(),
                binding.passwordTextInput.text.toString()
            )
        }
        binding.signInText.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }

        return binding.root
    }
}