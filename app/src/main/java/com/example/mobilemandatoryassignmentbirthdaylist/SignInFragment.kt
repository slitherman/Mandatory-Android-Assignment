package com.example.mobilemandatoryassignmentbirthdaylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentRegisterBinding
import com.example.mobilemandatoryassignmentbirthdaylist.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _bindingSignIn: FragmentSignInBinding? = null
    private lateinit var _auth: FirebaseAuth
    private val binding get() = _bindingSignIn!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _auth = Firebase.auth
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

        _bindingSignIn = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerTv = binding.textViewRegister

        val emailEt = binding.emailEt
        val passEt = binding.passET
        val confirmPassEt = binding.confirmPassEt
        val logInBtn = binding.buttonSignIn

        logInBtn.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passEt.text.toString()
            val confirm = confirmPassEt.text.toString()
            if(email.isEmpty()) {
                Toast.makeText(requireContext(),"Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                Toast.makeText(requireContext(),"Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(email.isEmpty()) {
                Toast.makeText(requireContext(),"Enter confirm password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(email,password,confirm)

        }
        registerTv.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        _auth.addAuthStateListener { auth ->
            val currentUser = _auth.currentUser
            if (currentUser != null) {
                val action = SignInFragmentDirections.actionSignInFragmentToFirstFragment()
                findNavController().navigate(action)
            }
        }
    }

    fun loginUser(email: String, password: String, confirmPassWord: String) {



        if (password == confirmPassWord)
        {

            _auth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = _auth.currentUser

                    } else {

                        val text = "Login Failed!"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(requireContext(), text, duration)
                        toast.show()

                        Log.w("Login", "signInWithEmail:failure", task.exception)
                    }
                }
        } else {

            val text = "Passwords do not match"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(requireContext(), text, duration)
            toast.show()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}