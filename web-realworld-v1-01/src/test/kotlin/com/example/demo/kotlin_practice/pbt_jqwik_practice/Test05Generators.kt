package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.Assume
import net.jqwik.api.Builders
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.PropertyDefaults
import net.jqwik.api.Provide
import net.jqwik.api.constraints.Size
import net.jqwik.api.constraints.UniqueElements
import net.jqwik.kotlin.api.JqwikIntRange
import net.jqwik.kotlin.api.any
import net.jqwik.kotlin.api.anyForType
import net.jqwik.kotlin.api.combine
import net.jqwik.kotlin.api.ofLength
import net.jqwik.kotlin.api.use
import org.assertj.core.api.Assertions.assertThat

//
// https://johanneslink.net/property-based-testing-in-kotlin/#generators-aka-arbitraries
//
@PropertyDefaults(tries = 10)
class Test05Generators {
    //
    // 自作ジェネレータ-00
    //
    @Provide
    fun playersByType00(): Arbitrary<PbtPlayer> = anyForType<PbtPlayer>()
    @Property
    fun playersFromType00(@ForAll("playersByType00") player: PbtPlayer) {
        // println(player)
    }

    //
    // 自作ジェネレータ-01
    //
    @Provide
    fun pbtPlayers01() = combine(
        String.any().alpha().numeric().ofLength(1..12), // nicknames
        Int.any(0..999), // ranking
        Arbitraries.of("dealer", "forehand", "middlehand"), // position
    ) { n, r, p -> PbtPlayer(n, r, p) }
    @Property
    fun validPlayers01(@ForAll("pbtPlayers01") player: PbtPlayer) {
        // println(player)
    }

    //
    // 自作ジェネレータ-02
    //
    @Provide
    fun pbtPlayers02() = combine(
        Arbitraries.strings().ascii().ofLength(2..42), // nicknames
        Int.any(0..999), // ranking
        Arbitraries.of("dealer", "forehand", "middlehand"), // position
    ) { n, r, p -> PbtPlayer(n, r, p) }
    @Property
    fun validPlayers2(@ForAll("pbtPlayers02") player: PbtPlayer) {
        // println(player)
    }

    //
    // FlatMap
    // T -> U
    //
    @Provide
    fun listWithValidIndex(): Arbitrary<Pair<List<Int>, Int>> {
        val lists = Int.any().list().uniqueElements().ofMinSize(1)
        return lists.flatMap { list ->
            Int.any(0 until list.size).map { index -> Pair(list, index) }
        }
    }
    @Property
    fun `Listの要素番号をランダム生成`(@ForAll("listWithValidIndex") listWithIndex: Pair<List<Int>, Int>) {
        val (list, index) = listWithIndex
        val element = list[index]
        assertThat(list.indexOf(element)).isEqualTo(index)
    }

    //
    // Assumptions
    //
    // Assumeによって結局Propertyテストをするか決定する
    // たくさん捨てられたら怒られる
    // (捨て過ぎ注意)
    //
    @Property
    fun `Listの要素番号をランダム生成2`(
        @ForAll @UniqueElements @Size(max = 10) list: List<Int>,
        @ForAll @JqwikIntRange(max = 9) index: Int
    ) {
        Assume.that(index >= 0 && index < list.size)
        val element = list[index]
        assertThat(list.indexOf(element)).isEqualTo(index)
    }

    //
    // Combine 自作ジェネレータ-03
    //
    // Builderパターン
    // これでcombineを使わずに済む
    //
    class PbtPlayerBuilder() {
        var nickname: String = "Joe"
        var ranking: Int = 1
        var position: String = "middlehand"
        fun withNickName(nickname: String): PbtPlayerBuilder {
            this.nickname = nickname
            return this
        }
        fun withRanking(ranking: Int): PbtPlayerBuilder {
            this.ranking = ranking
            return this
        }
        fun withPosition(position: String): PbtPlayerBuilder {
            this.position = position
            return this
        }
        fun build(): PbtPlayer = PbtPlayer(nickname, ranking, position)
    }
    @Provide
    fun pbtPlayers03(): Arbitrary<PbtPlayer> {
        val builder = Builders.withBuilder { PbtPlayerBuilder() }
        return builder
            .use(Arbitraries.strings().ascii().ofLength(2..42)) { b, n -> b.withNickName(n) }
            .use(Int.any(0..999)) { b, r -> b.withRanking(r) }
            .use(Arbitraries.of("dealer", "forehand", "middlehand")) { b, p -> b.withPosition(p) }
            .build { it.build() }
    }
    @Property
    fun validPlayers03(@ForAll("pbtPlayers03") player: PbtPlayer) {
        // println(player)
    }
}
//
// ノート
//
// anyForTypeでいい感じに型に合った適当なやつを入れてサンプルを生成してくれる
//
// 関数Xを@ForAll("関数X")と指定するために
// - 関数Xとプロパティと同じクラスに設置する
// - 関数XはArbitrary<T>を返す
// - 関数Xに@Provideアノテーションを付ける
//
// Stringの場合、空文字やprint不可能なUnicode文字も入る可能性がある
// Intの場合、負の数や0もあり得る
//
// combine()
// https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/combine.html
//
// スニペット
// Int.any(2..50000).map {it * 2} // 2~50000の偶数
// Int.any(1..Int.MAX_VALUE).map { it.toString(16) } // Intの16進数文字
//
// Doubleの長さ10のList生成
// - Double.any().list().ofSize(10)
// - Double.any().array(Array<Double>::class.java).ofSize(10)
// - Double.any().array<Double, Array<Double>>().ofSize(10)
//
