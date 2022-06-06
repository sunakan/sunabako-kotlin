package com.example.demo.kotlin_practice.jackson_practice

import arrow.core.Either
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

//
// https://www.baeldung.com/jackson-deserialization
//
class DesirializeExample {
    data class DummyUser(
        @JsonProperty("id") val id: Int,
        @JsonProperty("name") val name: String,
        @JsonProperty("tags") val tags: List<String>,
    )
    data class DummyItem(
        @JsonProperty("id") val id: Int,
        @JsonProperty("itemName") val itemName: String,
        @JsonProperty("owner") val owner: DummyUser,
    )

    data class NullableDummyUser(
        @JsonProperty("id") val id: Int?,
        @JsonProperty("name") val name: String?,
        @JsonProperty("tags") val tags: List<String?>?,
    )
    data class NullableDummyItem(
        @JsonProperty("id") val id: Int?,
        @JsonProperty("itemName") val itemName: String?,
        @JsonProperty("owner") val owner: NullableDummyUser?,
    )

    @Test
    fun example() {
        val json = """
            {
               "id": 1,
               "itemName": "theItem",
               "owner": {
                 "id": 2,
                 "name": "theUser",
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        val itemWithOwner = ObjectMapper().readValue<DummyItem>(json)
        assertThat(itemWithOwner.id).isEqualTo(1)
        assertThat(itemWithOwner.owner.id).isEqualTo(2)

        // Nullable版
        val itemWithOwner2 = ObjectMapper().readValue<NullableDummyItem>(json)
        assertThat(itemWithOwner2.id).isEqualTo(1)
        assertThat(itemWithOwner2.owner?.id).isEqualTo(2)
    }

    @Test
    fun `idが数字のStringの場合でも、Intにマッピング可能`() {
        val json = """
            {
               "id": "123",
               "itemName": "theItem",
               "owner": {
                 "id": 2,
                 "name": "theUser",
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        val itemWithOwner = ObjectMapper().readValue<DummyItem>(json)
        assertThat(itemWithOwner.id).isEqualTo(123)

        // Nullable版
        val itemWithOwner2 = ObjectMapper().readValue<NullableDummyItem>(json)
        assertThat(itemWithOwner2.id).isEqualTo(123)
    }

    @Test
    fun `idが数字にできないStringの場合、InvalidFormatExceptionが投げられる`() {
        val json = """
            {
               "id": "a1",
               "itemName": "theItem",
               "owner": {
                 "id": 2,
                 "name": "theUser",
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        when (val result = Either.catch { ObjectMapper().readValue<DummyItem>(json) }) {
            is Either.Right -> { fail() }
            is Either.Left -> {
                assertThat(result.value).isExactlyInstanceOf(InvalidFormatException::class.java)
            }
        }

        // Nullable版
        // nullにならず、例外を投げる
        when (val result = Either.catch { ObjectMapper().readValue<NullableDummyItem>(json) }) {
            is Either.Right -> { fail() }
            is Either.Left -> {
                assertThat(result.value).isExactlyInstanceOf(InvalidFormatException::class.java)
            }
        }
    }

    @Test
    fun `Int部分が欠損していた場合、zero値が入るが、Nullableの場合はnullが入る`() {
        val json = """
            {
               "itemName": "theItem",
               "owner": {
                 "name": "theUser",
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        val itemWithOwner = ObjectMapper().readValue<DummyItem>(json)
        assertThat(itemWithOwner.id).isEqualTo(0)
        assertThat(itemWithOwner.owner.id).isEqualTo(0)

        // Nullable版
        val itemWithOwner2 = ObjectMapper().readValue<NullableDummyItem>(json)
        assertThat(itemWithOwner2.id).isNull()
        assertThat(itemWithOwner2.owner?.id).isNull()
    }

    @Test
    fun `String部分が欠損していた場合、ValueInstantiationExceptionが投げられるが、Nullable版はnullが入る`() {
        val json = """
            {
               "id": 1,
               "owner": {
                 "id": 2,
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        when (val result = Either.catch { ObjectMapper().readValue<DummyItem>(json) }) {
            is Either.Right -> { fail() }
            is Either.Left -> {
                assertThat(result.value).isExactlyInstanceOf(ValueInstantiationException::class.java)
            }
        }

        // Nullable版
        val itemWithOwner2 = ObjectMapper().readValue<NullableDummyItem>(json)
        assertThat(itemWithOwner2.itemName).isNull()
        assertThat(itemWithOwner2.owner).isNotNull
        assertThat(itemWithOwner2.owner?.name).isNull()
    }

    @Test
    fun `Int部分がnullだった場合、zero値が入るが、Nullableの場合はnullが入る`() {
        val json = """
            {
               "id": null,
               "itemName": "theItem",
               "owner": {
                 "id": null,
                 "name": "theUser",
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        val itemWithOwner = ObjectMapper().readValue<DummyItem>(json)
        assertThat(itemWithOwner.id).isEqualTo(0)
        assertThat(itemWithOwner.owner.id).isEqualTo(0)

        // Nullable版
        val itemWithOwner2 = ObjectMapper().readValue<NullableDummyItem>(json)
        assertThat(itemWithOwner2.id).isNull()
        assertThat(itemWithOwner2.owner?.id).isNull()
    }

    @Test
    fun `String部分がnullだった場合、ValueInstantiationExceptionが投げられるが、Nullable版はnullが入る`() {
        val json = """
            {
               "id": 1,
               "itemName": null,
               "owner": {
                 "id": 2,
                 "name": null,
                 "tags": ["tag01"]
               }
            } 
        """.trimIndent()
        // ヌルポではない
        when (val result = Either.catch { ObjectMapper().readValue<DummyItem>(json) }) {
            is Either.Right -> { fail() }
            is Either.Left -> {
                assertThat(result.value).isExactlyInstanceOf(ValueInstantiationException::class.java)
            }
        }

        // Nullable版
        val itemWithOwner2 = ObjectMapper().readValue<NullableDummyItem>(json)
        assertThat(itemWithOwner2.itemName).isNull()
        assertThat(itemWithOwner2.owner).isNotNull
        assertThat(itemWithOwner2.owner?.name).isNull()
    }
}
