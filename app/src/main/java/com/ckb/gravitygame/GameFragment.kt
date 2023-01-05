package com.ckb.gravitygame

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ckb.gravitygame.Result
import com.ckb.gravitygame.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var gameBinding: FragmentGameBinding
    private lateinit var navController: NavController
    private lateinit var myViewModel: MyViewModel
    private var resultTime : Int = 0
    private val resultTimer = object : CountDownTimer(1000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            //No action
        }

        override fun onFinish() {
            resultTime+=1
            gameBinding.resultTimerText.text = resultTime.toString()
            this.cancel()
            this.start()
        }
    }

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
        //return inflater.inflate(R.layout.fragment_game, container, false)

        resultTimer.start()

        gameBinding = FragmentGameBinding.inflate(inflater, container, false)

        return gameBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        // Pause
        gameBinding.pauseButton.setOnClickListener{
            gameBinding.pauseFrameLayout.visibility =  View.VISIBLE
            resultTimer.cancel()
            gameBinding.mySurfaceView.stop()
        }

        gameBinding.continuePauseButton.setOnClickListener{
            gameBinding.pauseFrameLayout.visibility =  View.GONE
            resultTimer.start()
            gameBinding.mySurfaceView.start()
        }

        gameBinding.quitPauseButton.setOnClickListener{
            navController.navigate(R.id.action_gameFragment_to_menuFragment)
        }

        //Navigation
        gameBinding.mySurfaceView.listener.observe(viewLifecycleOwner, Observer {
            if(gameBinding.mySurfaceView.player.health == 0)
            {
                myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
                var myModel = myViewModel.myLiveModel.value
                if (myModel!=null)
                {
                    myModel.score = resultTime

                    var result = Result(myModel.name, myModel.score)
                    myModel.leaderboardList.add(result)
                }

                navController.navigate(R.id.action_gameFragment_to_resultFragment)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}