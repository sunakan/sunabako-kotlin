package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.domains.Domain
import net.jqwik.api.domains.DomainContextBase
import net.jqwik.api.domains.DomainList
import net.jqwik.api.statistics.Statistics
import net.jqwik.kotlin.api.any
import net.jqwik.kotlin.api.combine
import org.assertj.core.api.Assertions.assertThat

//
// https://johanneslink.net/property-based-testing-in-kotlin/#providing-generators-through-domain-contexts
//
class PokerDomainProvider : DomainContextBase() {
    //
    // トランプのカード
    //
    @Provide
    fun cards(): Arbitrary<PbtPlayingCard> =
        combine(Enum.any(), Enum.any()) { s: Suit, r: Rank ->
            PbtPlayingCard(s, r)
        }.withoutEdgeCases()

    //
    // 山札
    // Suit,Rankの組み合わせがユニークなカードを52枚(4*13 = 52)
    // (PbtPlayingCardにCompareToがあるから比較できる)
    //
    @Provide
    fun decks(): Arbitrary<List<PbtPlayingCard>> = cards().list().uniqueElements().ofSize(52)

    //
    // 手札
    // 山札の一番上から5枚(subList(0, 5))
    //
    @Provide
    fun hands(): Arbitrary<PbtHand> = decks()
        .map { deck: List<PbtPlayingCard> -> PbtHand(deck.subList(0, 5)) }

    //
    // 2人分の手札
    // 山札の一番上から5枚(subList(0, 5))
    // 次の山札の一番上から5枚(subList(5, 10))
    //
    @Provide
    fun pairOfHands(): Arbitrary<Pair<PbtHand, PbtHand>> = decks()
        .map { deck: List<PbtPlayingCard> ->
            val first = PbtHand(deck.subList(0, 5))
            val second = PbtHand(deck.subList(5, 10))
            Pair(first, second)
        }
}

//
// ジェネレータそのものテスト
//
// Domainを指定すると、組み込みジェネレータが使用できない
// 使用できるようにするために、 @Domain(DomainContext.Global::class)をコメントイン
//
@DomainList(
    Domain(PokerDomainProvider::class),
    // Domain(DomainContext.Global::class)
)
class Test06DomainSpecificGenerators {
    //
    // @Provide
    // fun cards(): Arbitrary<PbtPlayingCard> = ...
    // があるからいい感じに動く
    //
    // デフォの1000回よりも少なく、網羅的に生成できるならそっちを優先される
    // そのため52回しか生成されない(しかも網羅済み)
    @Property
    fun `52枚カードが生成される`(@ForAll card: PbtPlayingCard) {
        Statistics.collect(card)
    }

    //
    // @Provide
    // fun decks(): Arbitrary<List<PbtPlayingCard>> = ...
    // があるからいい感じに動く
    //
    @Property
    fun `シャッフルされた山札が生成される`(@ForAll deck: List<PbtPlayingCard>) {
        assertThat(deck).hasSize(52)
        assertThat(HashSet(deck)).hasSize(52)
    }

    //
    // @Provide
    // fun hands(): Arbitrary<PbtHand> = ...
    // があるからいい感じに動く
    //
    @Property
    fun `手札は5枚のユニークなカードである`(@ForAll hand: PbtHand) {
        assertThat(hand.show()).hasSize(5)
        // Setに入れることで重複していると減る(CompareToが実装されてるため)
        assertThat(HashSet(hand.show())).hasSize(5)
    }

    //
    // @Provide
    // fun pairOfHands(): Arbitrary<Pair<PbtHand, PbtHand>> = ...
    // があるからいい感じに動く
    //
    @Property
    fun `2人分の手札は重複しない`(@ForAll twoHands: Pair<PbtHand, PbtHand>) {
        val first = twoHands.first
        val second = twoHands.second
        assertThat(first.show()).hasSize(5)
        assertThat(second.show()).hasSize(5)
        // 1人目と2人目は重複しないことをテストしている
        assertThat(first.show()).doesNotContainAnyElementsOf(second.show())
    }
}
