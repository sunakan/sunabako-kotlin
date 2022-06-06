package com.example.demo.shared

// 参考
// Fault Tree Analysis
// https://en.wikipedia.org/wiki/Fault_tree_analysis

//
// ErrorEvent
//  |--Basic: エラー：通常発生すると予想されるエラー(ビジネスロジックでのエラー)(例：バリデーションエラー、権限無し)
//  |--Defect: 欠陥：想定外やBug(Error/Failureのどちらかに分類していきたい)
//  |--Failure: 故障：システムコンポーネントまたは要素の障害またはエラー(例：DBパスワードが間違っている、DBコネクションエラー)
//
// 運用
// 無印(With無し): 基本
// ***WithErrorEvent: ErrorEventをWrap
// ***WithThrowable: 利用するJava/Kotlinの処理がThrowable(With無し)を投げる可能性がある時try/catchしてWrap
//
interface ErrorEvent {
    interface Basic : ErrorEvent
    interface BasicValidationError : ErrorEvent {
        val key: String
        val message: String
    }
    interface BasicWithErrorEvent : ErrorEvent { val cause: ErrorEvent }
    interface BasicWithThrowable : ErrorEvent { val cause: Throwable }

    interface Defect : ErrorEvent
    interface DefectWithErrorEvent : ErrorEvent { val cause: ErrorEvent }
    interface DefectWithThrowable : ErrorEvent { val cause: Throwable }

    interface Failure : ErrorEvent
    interface FailureWithErrorEvent : ErrorEvent { val cause: ErrorEvent }
    interface FailureWithThrowable : ErrorEvent { val cause: Throwable }

    // 複数ありえる場合
    interface ErrorEvents : ErrorEvent { val errors: List<ErrorEvent> }
    interface ValidationErrors : ErrorEvent { val errors: List<BasicValidationError> }
}
