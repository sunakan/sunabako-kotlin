package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.ForAll
import net.jqwik.api.Property

//
// https://johanneslink.net/property-based-testing-in-kotlin/#metamorphic-properties
//
class Test07MetamorphicProperties {
    //
    // 合計
    //
    fun sum(vararg numbers: Int) = numbers.sum()

    //
    // t_wadaさんのTDDでは三角測量てきなやつがあった
    // PBTを利用すると。。
    //
    // メタモルフィック特性の基本的な考え方は、
    // - 逆演算
    // - べき乗
    // - 可換性
    // といったよく知られたパターンが背景にある
    @Property
    fun `合計はXで更にenhanceされる`(@ForAll list: IntArray, @ForAll x: Int): Boolean {
        val original = sum(*list)
        val sum = sum(*list, x)
        return sum == original + x
    }
}
