package com.example.demo.kotlin_practice.pbt_jqwik_practice

//
// プロジェクト
//
class PbtProject(val name: String, membersLimit: Int = 10) {
    private val membersLimit: Int
    private val members: MutableSet<PbtUser> = HashSet()

    init {
        require(name.isNotBlank()) { "Project名は1文字以上である必要があります" }
        this.membersLimit = membersLimit
    }

    //
    // プロジェクトにメンバーを追加
    //
    fun addMember(newMember: PbtUser) {
        check(members.size < membersLimit) { "もうプロジェクトにメンバーは入りません(Max: $membersLimit)" }
        check(members.all { m -> m.email != newMember.email }) { "${newMember.email}を持つメンバーはこのプロジェクトに居ます" }
        members.add(newMember)
    }

    //
    // ユーザーがプロジェクトメンバーかどうか
    //
    fun isMember(user: PbtUser) = members.contains(user)
}

//
// ノート
//
// require(value: Boolean)
// - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/require.html
// check(value: Boolean)
// - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/check.html
// Kotlin の require, check, assert 関数の使い分け
// - https://t-keita.hatenadiary.jp/entry/2020/12/05/223942
//
// all
// - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/all.html
