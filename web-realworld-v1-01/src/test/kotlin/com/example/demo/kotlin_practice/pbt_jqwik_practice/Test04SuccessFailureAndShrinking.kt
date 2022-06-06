package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import org.assertj.core.api.Assertions.assertThat

//
// https://johanneslink.net/property-based-testing-in-kotlin/#success-failure-and-shrinking
//
class Test04SuccessFailureAndShrinking {
    @Property
    fun `Listの反転はすべての要素を維持する`(@ForAll list: List<Int>) {
        assertThat(list.reversed()).containsAll(list)
    }
}
