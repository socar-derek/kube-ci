package com.derek.kotlin.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull
import java.lang.reflect.Constructor
import javax.validation.constraints.NotEmpty

data class BoardRequest(
    @NotEmpty
    val title: String,
    @NotEmpty
    val contents: String
)