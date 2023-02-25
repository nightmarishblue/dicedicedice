package com.midnight.dicedicedice.ui.home

import com.midnight.dicedicedice.R as nativeR
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.midnight.dicedicedice.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.diceRollResults
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.addDiceButton.setOnClickListener {
            var num = 0
            if (binding.diceNumber.text.toString() != "") num = binding.diceNumber.text.toString().toInt()
            binding.diceNumber.setText(minOf(100, num + 1).toString())
        }

        binding.removeDiceButton.setOnClickListener {
            var num = 0
            if (binding.diceNumber.text.toString() != "") num = binding.diceNumber.text.toString().toInt()
            binding.diceNumber.setText(minOf(100, maxOf(0, num - 1)).toString())
        }
        val rollHistory = arrayListOf<String>()

        val rollHistAdapter : ArrayAdapter<String> = ArrayAdapter(context!!, nativeR.layout.hist_list_view, rollHistory.asReversed())
        //val myAdapter : ArrayAdapter<String> = ArrayAdapter(context!!, R.layout.simple_list_item_1, listOfStuff)
        binding.rollDiceButton.setOnClickListener {
            var numDice = 0
            if (binding.diceNumber.text.toString() != "") numDice = binding.diceNumber.text.toString().toInt()
            numDice = minOf(100, numDice)

            // move current data into the other text box.
            if (binding.successCount.text != "") {
                if (rollHistory.size == 10) {
                    rollHistory.removeFirst()
                }
                rollHistory.add("${binding.diceRollResults.text.length / 3}d ${binding.diceRollResults.text} ${binding.successCount.text}"); rollHistAdapter.notifyDataSetChanged()
            }

            if (numDice != 0) {
                // generate the die results
                val dieRoll: ArrayList<Int> = arrayListOf()
                for (i in 1..numDice) {
                    dieRoll.add((1..6).random())
                }
                var successes = 0
                for (roll in dieRoll) {
                    if (roll in arrayOf(2, 4)) successes += 1
                    if (roll == 6) successes += 2
                }

                binding.diceRollResults.text = dieRoll.toString()
                binding.successCount.text = successes.toString()
            } else {
                binding.diceRollResults.text = "do the dice thing"
                binding.successCount.text = ""
            }
            binding.diceNumber.setText(numDice.toString())
            //val myAdapter : ArrayAdapter<String> = ArrayAdapter(context!!, R.layout.simple_list_item_1, listOfStuff)
            //binding.ting.adapter = myAdapter
        }

        //val myAdapter : ArrayAdapter<String> = ArrayAdapter(context!!, R.layout.simple_list_item_1, listOfStuff)
        binding.rollHistory.adapter = rollHistAdapter
        //myAdapter.notifyDataSetChanged()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}