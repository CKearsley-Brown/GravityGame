package com.ckb.gravitygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ckb.gravitygame.databinding.FragmentGameBinding
import com.ckb.gravitygame.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var resultBinding: FragmentResultBinding
    private lateinit var navController: NavController
    private lateinit var myViewModel: MyViewModel

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
        //return inflater.inflate(R.layout.fragment_result, container, false)

        resultBinding = FragmentResultBinding.inflate(inflater, container, false)
        return resultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        var myModel = myViewModel.myLiveModel.value

        myViewModel.myLiveModel.observe(viewLifecycleOwner, Observer {
            if (myModel!= null)
            {
                if (myModel.name != "Player")
                    resultBinding.resultText.text = myModel.name + ", you got"

                resultBinding.numText.text = "" + myModel.score + " seconds"
            }
        })

        if (myModel != null) {
            if(myModel.score <= 9)
                resultBinding.infoText.text = "Sir Isaac Newton was born in 1643 and produced the theory of gravitation in 1684"
            else if (myModel.score <= 19 && myModel.score >= 10 )
                resultBinding.infoText.text = "Gravity makes waves that move at light speed"
        }

        navController = findNavController()

        resultBinding.tryAgainButton.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_gameFragment)
        }

        resultBinding.quitButton.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_menuFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}