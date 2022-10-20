package com.angrymause.wwa_project.ui.fragment.gamescreens

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.angrymause.wwa_project.R
import com.angrymause.wwa_project.databinding.FragmentGameBinding
import com.angrymause.wwa_project.model.gamemodel.GameModel
import com.angrymause.wwa_project.ui.fragment.BaseFragment

private const val ALPHA = 0.1f

class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {
    private lateinit var buttons: List<TextView>
    private lateinit var cards: List<GameModel>
    private var indexOfSingleSelectedCard: Int? = null
    private val images =
        mutableListOf(R.drawable.ic_heart, R.drawable.ic_rebbit, R.drawable.ic_egglant)


    companion object {
        fun newInstance(): GameFragment {
            return GameFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDataArrays()
        startGame()
    }

    private fun setUpDataArrays() {
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
    }

    private fun startGame() {
        val timer =
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.refreshTv.text = (millisUntilFinished / 1000).toString()
                    cards.forEachIndexed { index, card ->
                        val button = buttons[index]
                        button.setBackgroundResource(card.identifier)
                    }
                }
                override fun onFinish() {
                    binding.refreshTv.isVisible = false
                    onCardClicked()
                }
            }
        timer.start()
    }

    private fun onCardClicked() {
        buttons.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                updateModels(index)
                updateViews()
            }
            updateViews()
        }


    }


    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = ALPHA
            }
            button.setBackgroundResource(if (card.isFaceUp) card.identifier else R.drawable.ic_android)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        if (card.isFaceUp) {
            return
        }
        indexOfSingleSelectedCard = if (indexOfSingleSelectedCard == null) {
            restoreCards()
            position
        } else {
            checkIsMatch(indexOfSingleSelectedCard!!, position)
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

    private fun checkIsMatch(position1: Int, position2: Int) {
        for (card in cards) {
            if (cards[position1].identifier == cards[position2].identifier) {
                cards[position1].isMatched = true
                cards[position2].isMatched = true
            }
        }

    }
}




