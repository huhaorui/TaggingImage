package com.huhaorui.taggingimage.common

import com.huhaorui.taggingimage.common.ResponseToken

object Tokens {
    val validateError = ResponseToken(400, "validate_error", "validate_error")
    val ok = ResponseToken(200, "ok", "ok")
    val failed = ResponseToken(403, "failed", "failed")

    object User {
    }
}