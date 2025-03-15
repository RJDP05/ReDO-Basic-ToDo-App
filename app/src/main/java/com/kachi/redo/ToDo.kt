package com.kachi.redo

import java.util.Date

data class ToDo(
    var id: Int,
    var title: String,
    var description: String,
    var time: Date
)
