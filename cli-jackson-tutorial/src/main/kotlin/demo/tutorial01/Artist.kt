package demo.tutorial01

import java.util.Date

data class Artist (
    val name: String,
    val dateOfBirth: Date,
    val appearanceOnStage: Map<String, String>,
)