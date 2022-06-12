package com.example.demo.kotlin_practice.di_pracitce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.function.Supplier
//
// Supplier<T>
// java.util.function.*の内の一つ
//
class DIableImpl() : Supplier<Int> {
    var status: Int = 100
    override fun get(): Int {
        return status % 2
    }
}

//
// Hogeのsaveがsに依存
// sはコンストラクタで依存を注入できるようにしている
// 自分でコンストラクタDI方法は引数初期値を指定
//   => これにより、アプリ側で通常利用時はHoge()と引数を意識しないで利用可能
//   => やらないこと：アプリ側で明示的にDIableImplをインスタンス化して渡すこと
// なので関数とかで見られる初期値とは微妙に違う
//
class Hoge(val s: Supplier<Int> = DIableImpl()) {
    fun save(): String {
        return when (s.get()) {
            0 -> { "偶数" }
            1 -> { "奇数" }
            else -> { "想定外" }
        }
    }
}

//
// Hogeのsaveをテストしたい
//
class LambdaDIPractice {
    // ざっくり方法1
    class ReturnZero : Supplier<Int> {
        override fun get(): Int = 0
    }
    @Test
    fun `0を返す時は"偶数"を返す`() {
        val result = Hoge(ReturnZero()).save()
        val expected = "偶数"
        assertThat(result).isEqualTo(expected)
    }
    // ざっくり方法2
    @Test
    fun `1を返す時は"奇数"を返す`() {
        val returnOne: Supplier<Int> = object : Supplier<Int> {
            override fun get(): Int = 1
        }
        val result = Hoge(returnOne).save()
        val expected = "奇数"
        assertThat(result).isEqualTo(expected)
    }
}
