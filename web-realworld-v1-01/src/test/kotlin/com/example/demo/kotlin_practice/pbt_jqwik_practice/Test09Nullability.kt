package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.WithNull

class Test09Nullability {
    //
    // 5%の確率でnullも出せる
    //
    @Property
    fun `nullも生成可能`(@ForAll nullOrString: String?) {
        // println(nullOrString)
    }

    //
    // Listの要素としてnullを出すには@WithNullが必要
    //
    @Property
    fun `listにnullを生成可能`(@ForAll list: List<@WithNull String?>) {
        // println(list)
    }
}
