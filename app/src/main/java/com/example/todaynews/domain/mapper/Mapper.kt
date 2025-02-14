package com.example.todaynews.domain.mapper

interface Mapper<DATA, DOMAIN> {
    fun toDomain(model: DATA): DOMAIN
    fun toData(model: DOMAIN): DATA
}