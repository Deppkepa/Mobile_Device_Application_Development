package com.example.navigationapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var returnData: String? = null

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
        val view = inflater.inflate(R.layout.fragment4, container, false)
        findNavController(this).currentBackStackEntry?.savedStateHandle
            ?.getLiveData<String>("result_from_activity")
            ?.observe(viewLifecycleOwner) { s -> returnData = s }
        if (returnData != null) {
            Toast.makeText(context, returnData, Toast.LENGTH_LONG).show()
        }
        val et: EditText = view.findViewById(R.id.et)
        val btn6: Button = view.findViewById(R.id.button6)
        btn6.setOnClickListener { _ ->
            // создаем бандл
            val bundle = Bundle()
            // вносим в него значения
            bundle.putString("arg1", et.text.toString())
            // выполняем навигацию с передачей данных
            findNavController().navigate(R.id.action_blankFragment4_to_main2Activity, bundle)
        }
        val btn6Up: Button = view.findViewById(R.id.button6_up)
        btn6Up.setOnClickListener { _ ->
            findNavController().navigateUp()
        }
        val btn7: Button = view.findViewById(R.id.button7)
        btn7.setOnClickListener { _ ->
            findNavController().navigate(R.id.action_blankFragment4_to_blankFragment32)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
