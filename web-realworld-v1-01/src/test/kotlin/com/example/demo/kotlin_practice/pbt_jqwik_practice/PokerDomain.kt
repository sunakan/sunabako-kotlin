package com.example.demo.kotlin_practice.pbt_jqwik_practice

//
// トランプのカードのマーク
//
enum class Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS
}
//
// トランプのカードの数字
//
enum class Rank {
    JOKER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}
//
// トランプのカード
//
data class PbtPlayingCard(val suit: Suit, val rank: Rank) : Comparable<PbtPlayingCard> {
    override fun compareTo(other: PbtPlayingCard): Int {
        return when (val suitCompare = suit.compareTo(other.suit)) {
            0 -> rank.compareTo(other.rank)
            else -> suitCompare
        }
    }
}
//
// ポーカーの手札
//
data class PbtHand(val cards: List<PbtPlayingCard>) {
    fun show() = cards.sorted()
}
