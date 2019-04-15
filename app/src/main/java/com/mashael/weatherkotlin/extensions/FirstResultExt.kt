package com.mashael.weatherkotlin.extensions

import com.mashael.weatherkotlin.R

inline fun <T,R:Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {
    for (element in this){
        val result=predicate(element)
        if (result !=null) return result
    }
    throw NoSuchElementException("No Element matching predicate was found.")
}
