package com.cdg.skeleton.common.extensions

import java.net.URLDecoder
import java.net.URLEncoder

fun String.urlEncoded(): String = URLEncoder.encode(this, Charsets.UTF_8)

fun String.urlDecoded(): String = URLDecoder.decode(this, Charsets.UTF_8)
