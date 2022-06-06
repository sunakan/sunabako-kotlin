package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Size
import org.assertj.core.api.Assertions.assertThat

//
// https://johanneslink.net/property-based-testing-in-kotlin/#a-short-intro-to-pbt
// A Short Intro to PBT
// Property Based Testing(PBT) の初歩
//
class Test02ReversePropertyTests {
    @Property
    fun `Listの反転はすべての要素を保持する`(@ForAll list: List<Int>) {
        assertThat(list.reversed()).containsAll(list)
    }

    @Property
    fun `Listの反転を2度すると戻る`(@ForAll list: List<Int>) {
        assertThat(list.reversed().reversed()).isEqualTo(list)
    }

    @Property
    fun `Listの反転前の最初の要素と反転後の最後の要素は一致する`(@ForAll @Size(min = 2) list: List<Int>) {
        val reversed = list.reversed()
        assertThat(reversed[0]).isEqualTo(list[list.size - 1])
        assertThat(reversed[list.size - 1]).isEqualTo(list[0])
    }
}

//
// ノート
//
// @ForAllでパラメータの種類を受け取り、適切なジェネレータを選択する
// それぞれのジェネレータには操作や制限が追加できるアノテーションが用意されている
// @Sizeで長さを操作
//
