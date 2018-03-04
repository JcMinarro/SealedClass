package com.jcminarro.sealedclass

import java.io.Serializable
import java.util.*

sealed class SomeSealedClass(val value: Int) : Serializable {

    object SomeObjectClass : SomeSealedClass(1)
    data class SomeDataClass(val someBoolean: Boolean,
                             val someDate: Date) : SomeSealedClass(2)
}