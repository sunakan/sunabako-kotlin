package com.example.demo.kotlin_practice.pbt_jqwik_practice

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

//
// https://johanneslink.net/property-based-testing-in-kotlin/#a-short-intro-to-pbt
// A Short Intro to PBT
// AssertJの素振り
// assertThatがそれ
//
class Test01ReverseExampleTests {
    @Test
    fun `Listは要素の反転が可能`() {
        val original: List<Int> = listOf(1, 2, 3)
        assertThat(original.reversed()).containsExactly(3, 2, 1)
    }
}
