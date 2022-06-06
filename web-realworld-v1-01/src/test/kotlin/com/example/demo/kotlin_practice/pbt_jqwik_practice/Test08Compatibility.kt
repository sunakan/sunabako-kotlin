package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Negative
import net.jqwik.api.constraints.Positive
import org.assertj.core.api.Assertions.assertThat

class Test08Compatibility {
    //
    // どっちの書き方も可能
    //
    @Property
    fun `positive は 0 より大きい`(@ForAll @Positive anInt: Int) = anInt > 0
    @Property
    fun `negative は 0 より小さい`(@ForAll @Negative anInt: Int) {
        assert(anInt < 0, { "$anInt should be < 0" })
    }

    //
    // 関数のテストも書ける
    //
    @Property
    fun `関数はStringを返す`(
        @ForAll func: ((Int, Int) -> String),
        @ForAll int1: Int,
        @ForAll int2: Int
    ) {
        assertThat(func(int1, int2)).isInstanceOf(String::class.java)
    }
}
