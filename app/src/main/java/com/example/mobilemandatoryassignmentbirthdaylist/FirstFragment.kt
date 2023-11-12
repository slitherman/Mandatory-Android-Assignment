package com.example.mobilemandatoryassignmentbirthdaylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobilemandatoryassignmentbirthdaylist.Models.DisplayablePerson
import com.example.mobilemandatoryassignmentbirthdaylist.Models.OnPersonInteractionListener
import com.example.mobilemandatoryassignmentbirthdaylist.Models.Person
import com.example.mobilemandatoryassignmentbirthdaylist.Models.PersonsAdapter
import com.example.mobilemandatoryassignmentbirthdaylist.Models.PersonsViewModel
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val PersonsViewModel: PersonsViewModel by activityViewModels()


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
            // You can update UI or perform additional actions based on the edit mode here
        }
        PersonsViewModel.personLiveData.observe(viewLifecycleOwner) { persons ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons.isNullOrEmpty()) View.GONE else View.VISIBLE

            if (persons != null) {
                val columns = resources.getInteger(R.integer.grid_columns)
                val adapter = PersonsAdapter(persons) { position ->



                    if (PersonsViewModel.isEditMode()) {
                        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                        findNavController().navigate(action)
                    } else {
                        val selectedPerson = persons[position]
                        PersonsViewModel.setFriendDetails(selectedPerson)
                        findNavController().navigate(R.id.action_FirstFragment_to_friendFragment)
                    }

                }


                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
                binding.recyclerView.adapter = adapter
            }
        }

        PersonsViewModel.errMsgLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        PersonsViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            PersonsViewModel.reload()
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