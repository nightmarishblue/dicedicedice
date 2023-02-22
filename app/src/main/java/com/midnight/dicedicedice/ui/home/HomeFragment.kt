package com.midnight.dicedicedice.ui.home

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.midnight.dicedicedice.databinding.FragmentHomeBinding
import kotlin.random.Random

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

        binding.rollDiceButton.setOnClickListener {
            var numDice = 0
            if (binding.diceNumber.text.toString() != "") numDice = binding.diceNumber.text.toString().toInt()
            numDice = minOf(100, numDice)

            // move current data into the other text box.
            if (binding.successCount.text != "") {
                val successCount = binding.successCount.text.toString()
                val dieResults = binding.diceRollResults.text.toString()
                val rollSize = dieResults.length / 3
                var oldHistory = binding.rollHistory.text.toString()
                //Toast.makeText(context, oldHistory.count{it == '\n'}.toString(), Toast.LENGTH_SHORT).show()
                if (oldHistory.count{it == '\n'} >= 8) {
                    oldHistory = oldHistory.split("\n").slice(0..6).joinToString("\n") + "\n"//.joinToString { "\n" } //.slice(0..6).joinToString { "\n" }

                }
                //val newHistory = text.split('\n').slice(0..1).joinToString("\n")
                val newHistory =  "${rollSize}d  $dieResults  $successCount\n${oldHistory}"
                binding.rollHistory.text = newHistory
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
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}