package edu.ktu.dogsfirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.ktu.dogsfirebase.databinding.FragmentSignInBinding
import edu.ktu.dogsfirebase.viewmodels.SignInViewModel

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        val viewModel: SignInViewModel by viewModels()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToDogListFragment())
            }
        }

        binding.signInBtn.setOnClickListener {
            viewModel.signIn(
                binding.emailTextInput.text.toString(),
                binding.passwordTextInput.text.toString()
            )
        }
        binding.signUpText.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        return binding.root
    }
}