package com.example.mobilemandatoryassignmentbirthdaylist

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobilemandatoryassignmentbirthdaylist.Models.PersonsAdapter
import com.example.mobilemandatoryassignmentbirthdaylist.Models.PersonsViewModel
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val PersonsViewModel: PersonsViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch = binding.switchEditMode
        switch.setOnCheckedChangeListener { _, isChecked ->
            PersonsViewModel.setEditMode(isChecked)

        }
        PersonsViewModel.personLiveData.observe(viewLifecycleOwner) { persons ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons.isNullOrEmpty()) View.GONE else View.VISIBLE

            if (persons != null) {
                val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                val column = if (isLandscape) {
                    resources.getInteger(R.integer.grid_columns_landscape)
                } else {
                    resources.getInteger(R.integer.grid_columns)
                }

                val adapter = PersonsAdapter(persons) { position ->
                    if (PersonsViewModel.isEditMode()) {
                        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                        findNavController().navigate(action)
                    } else {
                        val selectedPerson = persons[position]
                        PersonsViewModel.setFriendDetails(selectedPerson)
                        val action = FirstFragmentDirections.actionFirstFragmentToFriendFragment(position)
                        findNavController().navigate(action)
                    }
                }

                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), column)
                binding.recyclerView.adapter = adapter
                binding.swiperefresh.setOnRefreshListener {
                    PersonsViewModel.reload(auth.currentUser?.email!!)
                    binding.swiperefresh.isRefreshing = false
                }
            }
        }

        PersonsViewModel.errMsgLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        PersonsViewModel.reload(auth.currentUser?.email!!)

        binding.swiperefresh.setOnRefreshListener {
            PersonsViewModel.reload(auth.currentUser?.email!!)
            binding.swiperefresh.isRefreshing = false
        }
        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> PersonsViewModel.sortByName()
                1 -> PersonsViewModel.sortByAge()
                2 -> PersonsViewModel.sortByBirthday()
            }
        }

        binding.buttonFilter.setOnClickListener {
            val personFilterName = binding.edittextFilterName.text.toString().trim()
            val personFilterAge = binding.edittextFilterAge.text.toString().trim()
            PersonsViewModel.filterNameAndAge(personFilterName, personFilterAge)
        }

        binding.buttonAdd.setOnClickListener {

            val action = FirstFragmentDirections.actionFirstFragmentToThirdFragment()
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}