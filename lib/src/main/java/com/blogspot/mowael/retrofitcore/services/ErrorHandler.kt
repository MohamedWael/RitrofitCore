package com.blogspot.mowael.retrofitcore.services

interface ErrorHandler {
    var errorMsgStringRes: Int?
    var errorMsgString: String?
    var throwable: Throwable?
}