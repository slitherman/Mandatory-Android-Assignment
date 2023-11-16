package com.example.mobilemandatoryassignmentbirthdaylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mobilemandatoryassignmentbirthdaylist.Models.DisplayablePerson
import com.example.mobilemandatoryassignmentbirthdaylist.Models.PersonsViewModel
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentFirstBinding
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentFriendBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FriendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFriendBinding? = null
    private val PersonsViewModel: PersonsViewModel by activityViewModels()

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PersonsViewModel._friendDetails.observe(viewLifecycleOwner) { friendDetails ->

            binding.textViewName.text =  "Name: " + friendDetails.name
            val formattedBirthday = "%02d/%02d/%04d".format(
                friendDetails.birthMonth,
                friendDetails.birthDayOfMonth,
                friendDetails.birthYear)

            binding.textViewBirthday.text = " BirthDate: " +    formattedBirthday
            binding.textViewRemarks.text = " Remarks: " +  friendDetails.remarks
            binding.textViewUserId.text =  "Email: " +  friendDetails.userId
            binding.textViewId.text = "Id: " + friendDetails.id
        }

        binding.btnDelete.setOnClickListener {
            val bundle = requireArguments()
            val friendFragArg: FriendFragmentArgs = FriendFragmentArgs.fromBundle(bundle)
            val position = friendFragArg.position
            val PersonToDelete = PersonsViewModel[position]

            if (PersonToDelete != null) {
                PersonsViewModel.delete(PersonToDelete.id)
            }
            findNavController().popBackStack()

        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FriendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FriendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}