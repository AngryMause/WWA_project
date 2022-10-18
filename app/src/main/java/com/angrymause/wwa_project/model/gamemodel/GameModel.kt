package com.angrymause.wwa_project.model.gamemodel

data class GameModel(
    val identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false,
) {
}