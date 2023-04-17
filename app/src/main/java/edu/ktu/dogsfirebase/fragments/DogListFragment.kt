package edu.ktu.dogsfirebase.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ktu.dogsfirebase.R
import edu.ktu.dogsfirebase.databinding.FragmentDogListBinding
import edu.ktu.dogsfirebase.ui.adapters.DogAdapter
import edu.ktu.dogsfirebase.viewmodels.DogListViewModel

class DogListFragment : Fragment(), MenuProvider {

    val viewModel: DogListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDogListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = DogAdapter(DogAdapter.OnDeleteClickListener { dog ->
            viewModel.deleteDog(dog)
        })
        binding.dogRecyclerView.adapter = adapter
        binding.dogRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.dogs.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.addDogFab.setOnClickListener {
            findNavController().navigate(DogListFragmentDirections.actionDogListFragmentToAddDogFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(
            this,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_dog_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.sign_out)
        {
            viewModel.signOut()
            findNavController().navigate(DogListFragmentDirections.actionDogListFragmentToSignInFragment())
            return true
        }
        return false
    }
}