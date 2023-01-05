package com.ckb.gravitygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ckb.gravitygame.databinding.FragmentMenuBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var menuBinding: FragmentMenuBinding
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
        //return inflater.inflate(R.layout.fragment_menu, container, false)

        menuBinding = FragmentMenuBinding.inflate(inflater, container, false)

        return menuBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        menuBinding.playButton.setOnClickListener {
            myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

            var myModel = myViewModel.myLiveModel.value

            if (myModel!=null)
            {
                if (menuBinding.nameTextInputText.text?.length != 0)
                {
                    myModel.name = menuBinding.nameTextInputText.text.toString()
                } else
                {
                    myModel.name = "Player"
                }
            }

            navController.navigate(R.id.action_menuFragment_to_gameFragment)
        }

        menuBinding.howToButton.setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_howToFragment)
        }

        menuBinding.informationButton.setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_informationFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}