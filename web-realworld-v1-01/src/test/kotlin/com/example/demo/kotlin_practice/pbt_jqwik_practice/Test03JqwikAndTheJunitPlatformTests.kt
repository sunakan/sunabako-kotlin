package com.example.demo.kotlin_practice.pbt_jqwik_practice

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.NotBlank
import net.jqwik.api.constraints.NumericChars
import net.jqwik.api.constraints.Size
import net.jqwik.api.constraints.UniqueElements
import net.jqwik.web.api.Email
import org.assertj.core.api.Assertions.assertThat

//
// https://johanneslink.net/property-based-testing-in-kotlin/#jqwik-and-the-junit-platform
//
class Test03JqwikAndTheJunitPlatformTests {
    @Property(tries = 100)
    fun `Max10人のチームメンバーをProjectに追加できる`(
        @ForAll projectName: @NotBlank @NumericChars String,
        @ForAll emails: @Size(max = 10) @UniqueElements List<@Email String>,
    ) {
        val project = PbtProject(projectName)
        val users = emails.map { PbtUser(it) }.toList()
        for (user in users) {
            project.addMember(user)
        }
        for (user in users) {
            assertThat(project.isMember(user)).isTrue
        }
    }
}

//
// ノート
//
// @Propertyのtriesで試行回数を操作できる
// @ForAllでパラメータの種類を受け取り、適切なジェネレータを選択する
// @SizeでListの長さを操作できる
// @UniqueElementsでユニーク性を保証する
//
