package com.example.demo.modules.user.web

//
// 入力値エラー1
// - JSONがnull
// - JSON syntax がおかしい
//
// 入力値エラー2
// - JSON はあってるが、望んでいるフォーマットが異なる
// - JSON はあってて、望んでいるフォーマットだけど、必須項目が足りない
//
//
class RegisterHandlerTest {
    // @Test
    // fun `RequestBodyが無し(null)の場合、ResponseのBodyは空文字でStatusは400`() {
    //    val requestBody = null
    //    val response = RegisterHandler().handle(requestBody)
    //    val expected = ResponseEntity("", HttpStatus.valueOf(400))
    //    assertThat(response).isEqualTo(expected)
    // }
    // @Test
    // fun `RequestBodyのJSON syntaxが正しくない場合、ResponseのBodyは空文字でStatusは400`() {
    //    val requestBody = "--Invalid Json--"
    //    val response = RegisterHandler().handle(requestBody)
    //    val expected = ResponseEntity("", HttpStatus.valueOf(400))
    //    assertThat(response.body).isEqualTo(expected)
    // }
}
