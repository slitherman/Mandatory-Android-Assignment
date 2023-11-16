package com.example.mobilemandatoryassignmentbirthdaylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mobilemandatoryassignmentbirthdaylist.Models.DisplayablePerson
import com.example.mobilemandatoryassignmentbirthdaylist.Models.PersonsViewModel
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val PersonsViewModel: PersonsViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val Person = PersonsViewModel[position]

        binding.buttonUpdate.setOnClickListener {
            try {
                val Name = binding.editTextName.text.toString().trim()
                val Email = binding.editTextEmail.text.toString().trim()
                val DatePicker = binding.datePicker
                val Day = DatePicker.dayOfMonth
                val Month = DatePicker.month
                val Year = DatePicker.year
                val Remarks = binding.editTextRemarks.text.toString()

                Person?.let {
                    val updatedPerson = DisplayablePerson(it.id, Email, Name, Year, Month, Day,age = null, Remarks, pictureUrl = null)
                    Log.d("UpdatePerson", "update $updatedPerson")
                    PersonsViewModel.updatePeron(it.id, updatedPerson)
                    findNavController().popBackStack()
                }
            } catch (e: Exception) {
                // Handle or log the exception
                Log.e("UpdatePerson", "Error updating person: ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}