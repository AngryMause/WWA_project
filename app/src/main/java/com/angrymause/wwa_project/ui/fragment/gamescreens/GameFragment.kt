package com.angrymause.wwa_project.ui.fragment.gamescreens

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.angrymause.wwa_project.R
import com.angrymause.wwa_project.databinding.FragmentGameBinding
import com.angrymause.wwa_project.model.gamemodel.GameModel
import com.angrymause.wwa_project.ui.fragment.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {
    private lateinit var buttons: List<TextView>
    private lateinit var cards: List<GameModel>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()

    }

    private fun setUp() {
        val images = mutableListOf(R.drawable.ic_heart, R.drawable.ic_rebbit, R.drawable.ic_egglant)
        images.addAll(images)
        images.shuffle()
        buttons = listOf(binding.textView1,
            binding.textView2,
            binding.textView3,
            binding.textView4,
            binding.textView5,
            binding.textView6)
        cards = buttons.indices.map { index ->
            GameModel(images[index])
        }
        buttons.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                updateModels(index)
                updateViews()
            }
        }
    }


    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.0f

            }
            button.setBackgroundResource(if (card.isFaceUp) card.identifier else R.drawable.ic_android)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        if (card.isFaceUp) {
            Toast.makeText(requireContext(), "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }

        indexOfSingleSelectedCard = if (indexOfSingleSelectedCard == null) {
            restoreCards()
            position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }
}