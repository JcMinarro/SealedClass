package com.jcminarro.sealedclass

import org.apache.commons.lang3.SerializationUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

private const val SOME_OBJECT_CLASS_FILE = "someObjectClassFile"
private const val SOME_DATA_CLASS_FILE = "someDataClassFile"
private const val SANDBOX_DIR = "build/tmp/test/"

class SomeSealedClassTest {

    @Test
    fun testTypeOfSomeObjectClass() {
        val someObjectClass = SomeSealedClass.SomeObjectClass

        assertTrue(someObjectClass is SomeSealedClass.SomeObjectClass)
    }

    @Test
    fun testTypeOfSomeDataClass() {
        val someDataClass = SomeSealedClass.SomeDataClass(true, Date(3))

        assertTrue(someDataClass is SomeSealedClass.SomeDataClass)
        assertTrue(someDataClass.someBoolean)
        assertEquals(someDataClass.someDate, Date(3))
    }

    @Test
    fun testTypeOfSomeObjectClassStoredInDisk() {
        val someObjectClass = SomeSealedClass.SomeObjectClass
        storeSomeSealedClassIntoFile(SOME_OBJECT_CLASS_FILE, someObjectClass)
        val newSomeObjectClass = loadSomeSealedClassFromFile(SOME_OBJECT_CLASS_FILE)

        assertTrue(newSomeObjectClass is SomeSealedClass.SomeObjectClass)
    }

    @Test
    fun testTypeOfSomeDataClassStoredInDisk() {
        val someDataClass = SomeSealedClass.SomeDataClass(true, Date(3))
        storeSomeSealedClassIntoFile(SOME_DATA_CLASS_FILE, someDataClass)
        val newSomeDataClass = loadSomeSealedClassFromFile(SOME_DATA_CLASS_FILE)

        assertTrue(newSomeDataClass is SomeSealedClass.SomeDataClass)
        assertTrue(someDataClass.someBoolean)
        assertEquals(someDataClass.someDate, Date(3))
    }

    @Test
    fun testWhenWithSomeObjectClass() {
        val someObjectClass: SomeSealedClass = SomeSealedClass.SomeObjectClass

        val isSomeObjectClass = when (someObjectClass) {
            SomeSealedClass.SomeObjectClass -> true
            is SomeSealedClass.SomeDataClass -> false
        }

        assertTrue(someObjectClass is SomeSealedClass.SomeObjectClass)
        assertTrue(isSomeObjectClass)
    }

    @Test
    fun testWhenWithSomeDataClass() {
        val someDataClass: SomeSealedClass = SomeSealedClass.SomeDataClass(true, Date(3))

        val isSomeDataClass = when (someDataClass) {
            SomeSealedClass.SomeObjectClass -> false
            is SomeSealedClass.SomeDataClass -> true
        }

        assertTrue(someDataClass is SomeSealedClass.SomeDataClass)
        assertTrue((someDataClass as SomeSealedClass.SomeDataClass).someBoolean)
        assertEquals((someDataClass as SomeSealedClass.SomeDataClass).someDate, Date(3))
        assertTrue(isSomeDataClass)
    }

    @Test
    fun testWhenWithSomeObjectClassStoredInDisk() {
        val someObjectClass = SomeSealedClass.SomeObjectClass
        storeSomeSealedClassIntoFile(SOME_OBJECT_CLASS_FILE, someObjectClass)
        val newSomeObjectClass = loadSomeSealedClassFromFile(SOME_OBJECT_CLASS_FILE)

        val isSomeObjectClass = when (newSomeObjectClass) {
            SomeSealedClass.SomeObjectClass -> true
            is SomeSealedClass.SomeDataClass -> false
        }

        assertTrue(newSomeObjectClass is SomeSealedClass.SomeObjectClass)
        assertTrue(isSomeObjectClass)
    }

    @Test
    fun testWhenWithSomeDataClassStoredInDisk() {
        val someDataClass = SomeSealedClass.SomeDataClass(true, Date(3))
        storeSomeSealedClassIntoFile(SOME_DATA_CLASS_FILE, someDataClass)
        val newSomeDataClass = loadSomeSealedClassFromFile(SOME_DATA_CLASS_FILE)

        val isSomeDataClass = when (newSomeDataClass) {
            SomeSealedClass.SomeObjectClass -> false
            is SomeSealedClass.SomeDataClass -> true
        }

        assertTrue(newSomeDataClass is SomeSealedClass.SomeDataClass)
        assertTrue((newSomeDataClass as SomeSealedClass.SomeDataClass).someBoolean)
        assertEquals((newSomeDataClass as SomeSealedClass.SomeDataClass).someDate, Date(3))
        assertTrue(isSomeDataClass)
    }

    @Test
    fun testWhenWithIsWithSomeObjectClassStoredInDisk() {
        val someObjectClass = SomeSealedClass.SomeObjectClass
        storeSomeSealedClassIntoFile(SOME_OBJECT_CLASS_FILE, someObjectClass)
        val newSomeObjectClass = loadSomeSealedClassFromFile(SOME_OBJECT_CLASS_FILE)

        val isSomeObjectClass = when (newSomeObjectClass) {
            is SomeSealedClass.SomeObjectClass -> true
            is SomeSealedClass.SomeDataClass -> false
        }

        assertTrue(newSomeObjectClass is SomeSealedClass.SomeObjectClass)
        assertTrue(isSomeObjectClass)
    }

    private fun storeSomeSealedClassIntoFile(fileName: String, someSealedClass: SomeSealedClass) =
            File(SANDBOX_DIR).let {
                it.mkdirs()
                FileOutputStream(File(it, fileName)).write(SerializationUtils.serialize(someSealedClass))
            }

    private fun loadSomeSealedClassFromFile(fileName: String): SomeSealedClass =
            SerializationUtils.deserialize<SomeSealedClass>(FileInputStream(File(SANDBOX_DIR, fileName)))
}