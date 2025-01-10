package com.cdg.skeleton.common.response

data class ApiResponseDto<S> private constructor(
    val result: ResultType,
    val data: S? = null,
    val error: ErrorMessage? = null,
) {
    companion object {
        fun <S> success(): ApiResponseDto<S> {
            return ApiResponseDto(result = ResultType.SUCCESS)
        }

        fun <S> success(data: S): ApiResponseDto<S> {
            return ApiResponseDto(result = ResultType.SUCCESS, data = data)
        }

        fun <S> error(error: ErrorMessage): ApiResponseDto<S> {
            return ApiResponseDto(result = ResultType.ERROR, error = error)
        }
    }
}

enum class ResultType {
    SUCCESS,
    ERROR
}

data class ErrorMessage(
    val code: String,
    val message: String,
    val data: Any? = null,
)