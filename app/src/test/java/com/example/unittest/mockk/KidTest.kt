package com.example.unittest.mockk

import com.example.unittest.kotlin.Ext
import com.example.unittest.kotlin.Kid
import com.example.unittest.kotlin.Mother
import com.example.unittest.kotlin.Obj
import com.example.unittest.kotlin.Util
import com.example.unittest.kotlin.UtilCompanion
import com.example.unittest.kotlin.UtilCompanion2
import com.example.unittest.kotlin.UtilJava
import com.example.unittest.kotlin.UtilKotlin
import com.example.unittest.kotlin.UtilKotlinSingleton
import com.example.unittest.kotlin.extensionFunc2
import com.example.unittest.kotlin.topAdd
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.PrintStream

/**
 * 全局配置文件：src/main/resources/io/mockk/settings.properties
 * @author shouxianli on 2021/5/11.
 */
open class KidTest {
    @Test
    fun test1() {
        val mother = mockk<Mother>()
        val kid = Kid(mother)

        //类似于mockito的when...thenReturn
        every {
            mother.giveMoney()
        } returns 30

        //mockk是很严谨的，没有这个语句会导致verify inform方法失败
        every {
            mother.inform(any())
        } just Runs

        kid.wantMoney()

        verify { mother.inform(any()) }
        Assert.assertEquals(30, kid.money)
    }

    @Test
    fun test2() {
        //加上relaxed=true，在调用方法前，就不需要先指定运行方式了（即使用every语法）
        //或者使用relaxUnitFun，表示不需要指定无返回值的方法，有返回值的方法仍然需要使用every语法指定
        val mother = mockk<Mother>(relaxed = true)
        val kid = Kid(mother)

        //类似于mockito的when...thenReturn
        every {
            mother.giveMoney()
        } returns 30

        kid.wantMoney()

        //exactly指定调用次数
        verify(exactly = 1) {
            //可以一次验证多个方法
            mother.inform(any())
            mother.giveMoney()
        }

        //验证方法的顺序调用可以使用下面两个方法
//        verifyOrder {//只要giveMoney在inform后执行即可
//            mother.inform(any())
//            mother.giveMoney()
//        }
//        verifySequence {//inform的下一个执行的方法必须是giveMoney，比verifyOrder更严格
//            mother.inform(any())
//            mother.giveMoney()
//        }

        Assert.assertEquals(30, kid.money)
    }

    @Test
    fun testCapture() {
        val mother = mockk<Mother>(relaxed = true)
        val kid = Kid(mother)

        var slot = slot<Int>()

        every {
            mother.inform(capture(slot))
        } just Runs
        //等价于：
//        justRun {
//            mother.inform(capture(slot))
//        }

        kid.wantMoney()

        Assert.assertEquals(0, slot.captured)

        slot = slot()
        every {
            mother.add(capture(slot), any())
        } answers { //此处不能使用returns
            slot.captured + 10
        }

        print(mother.add(1, 2))
    }

    @Test
    fun testStatic() {
        val util = Util()

        //java的静态方法
        mockkStatic(UtilJava::class)
        every {
            UtilJava.ok()
        } returns "Joe"

        //kotlin的object中的方法，注意，方法必须添加@JvmStatic注解
        mockkStatic(UtilKotlin::class)
        every {
            UtilKotlin.ok()
        } returns "Tsai"

        //kotlin中的singleton
        mockkObject(UtilKotlinSingleton)
        every {
            UtilKotlinSingleton.ok()
        } returns "hello"

        //可以通过这种方式重新生成一个实例
        val singleton = mockk<UtilKotlinSingleton>()
        Assert.assertNotEquals(singleton, UtilKotlinSingleton)

        //kotlin的companion中的方法
        mockkObject(UtilCompanion)
        mockkObject(UtilCompanion.Companion)
        every {
            UtilCompanion.ok()
        } returns "haha"

        //kotlin的companion2中的static方法
        mockkObject(UtilCompanion2.Companion)
        every {
            UtilCompanion2.ok()
        } returns "haha"

        util.ok()

        verifySequence {
//            UtilJava.create()
            UtilJava.ok()
            UtilKotlin.ok()
            UtilKotlinSingleton.ok()
            UtilCompanion.ok()
            UtilCompanion2.ok()
        }

        Assert.assertEquals("Joe", UtilJava.ok())
        Assert.assertEquals("Tsai", UtilKotlin.ok())
        Assert.assertEquals("hello", UtilKotlinSingleton.ok())
        Assert.assertEquals("haha", UtilCompanion.ok())
    }

    @Test
    fun testObject() {
        mockkStatic(UtilJava::class)
        every {
            UtilJava.create()
        } returns mockk()

        mockkObject(UtilKotlinSingleton)
        every {
            UtilKotlinSingleton.ok()
        } returns ""

        UtilKotlinSingleton.ok()
        Assert.assertTrue(true)
    }

    @Test
    fun testSpy() {
        val mother = spyk(Mother())
        val kid = Kid(mother)

        excludeRecords {//不记录某些动作  #1
            mother.inform(any())
        }

        kid.wantMoney()

        verify {
//            mother.inform(eq(0))   // 若注释#1的话，则必须有这一句，否则报错 #2
            mother.giveMoney()
        }

        confirmVerified(mother)  //确保所有的记录都被verify
    }

    /**
     * mock构造函数
     */
    @Test
    fun testConstructor() {
        mockkConstructor(Mother::class)

        every {
            anyConstructed<Mother>().giveMoney()
        } returns 20
        //或者：
//        every {
//            constructedWith<Mother>().giveMoney()
//        } returns 20

        //针对不同的构造函数
//        every {
//            constructedWith<Mother>(OfTypeMatcher<String>(String::class)).giveMoney()
//        } returns 50
//        every {
//            constructedWith<Mother>(EqMatcher(10)).giveMoney()
//        } returns 70

        val mother = Mother()
        val kid = Kid(mother)

        kid.wantMoney()

        Assert.assertEquals(20, kid.money)
    }

    @Test
    fun testMethod() {
        val mother = spyk(Mother())
        every {
            mother.shopping(
                year = 2021,
                month = 5,
                day = any()
            )
        } answers { call ->
            val result = call.invocation.args.joinToString(separator = ",")
            println(result)
            ""
        }

        mother.shopping(2021, 5, 4)
        mother.shopping(2021, 6, 4)
    }

    @Test
    fun testHierarchicalMocking() {
        val addressBook = mockk<AddressBook> {
            every { contacts } returns listOf(
                mockk {
                    every { name } returns "John"
                    every { telephone } returns "123-456-789"
                    every { address.city } returns "New-York"
                    every { address.zip } returns "123-45"
                },
                mockk {
                    every { name } returns "Alex"
                    every { telephone } returns "789-456-123"
                    every { address } returns mockk {
                        every { city } returns "Wroclaw"
                        every { zip } returns "543-21"
                    }
                }
            )
        }
        println(addressBook)
    }

    /**
     * 扩展函数，情形1
     */
    @Test
    fun testExtensionMethod() {
        with(mockk<Ext>()) {
            val obj = Obj(5)
            every {
                obj.extensionFunc()
            } returns 11

            Assert.assertEquals(11, obj.extensionFunc())

            verify {
                obj.extensionFunc()
            }
        }
    }

    /**
     * 扩展函数，情形2，方式1
     */
    @Test
    fun testExtensionMethod2_1() {
        val obj = Obj(10)
        //包名+编译后的类名
        mockkStatic("com.example.unittest.kotlin.DemoKt")
        every {
            obj.extensionFunc2()
        } returns 33
        Assert.assertEquals(33, obj.extensionFunc2())
        verify {
            obj.extensionFunc2()
        }
    }

    /**
     * 扩展函数，情形2，方式2
     */
    @Test
    fun testExtensionMethod2_2() {
        val obj = Obj(10)
        mockkStatic(Obj::extensionFunc2)
        every {
            obj.extensionFunc2()
        } returns 33
        Assert.assertEquals(33, obj.extensionFunc2())
        verify {
            obj.extensionFunc2()
        }
    }

    @Test
    fun testTopFunc() {
        //包名+编译后的类名
        mockkStatic("com.example.unittest.kotlin.DemoKt")
        every {
            topAdd(ofType<Long>())
        } just Runs

//        Assert.assertEquals(10, topAdd(1))
        topAdd(100L)

        verify {
            topAdd(ofType<Long>())
        }
    }

    /**
     * 可变参数
     */
    @Test
    fun testVararg() {
        val obj = mockk<ClsWithManyMany>()

        every { obj.manyMany(5, 6, *varargAll { it == 7 }) } returns 3

        println(obj.manyMany(5, 6, 7)) // 3
        println(obj.manyMany(5, 6, 7, 7)) // 3
        println(obj.manyMany(5, 6, 7, 7, 7)) // 3

        every { obj.manyMany(5, 6, *anyVararg(), 7) } returns 4

        println(obj.manyMany(5, 6, 1, 7)) // 4
        println(obj.manyMany(5, 6, 2, 3, 7)) // 4
        println(obj.manyMany(5, 6, 4, 5, 6, 7)) // 4

        every { obj.manyMany(5, 6, *varargAny { nArgs > 5 }, 7) } returns 5

        println(obj.manyMany(5, 6, 4, 5, 6, 7)) // 5
        println(obj.manyMany(5, 6, 4, 5, 6, 7, 7)) // 5

        every {
            obj.manyMany(5, 6, *varargAny {
                if (position < 3) it == 3 else it == 4
            }, 7)
        } returns 6

        println(obj.manyMany(5, 6, 3, 4, 7)) // 6
        println(obj.manyMany(5, 6, 3, 4, 4, 7)) // 6
    }

    @Test
    fun testPrivateFunc() {
        val mother = spyk<Mother>(recordPrivateCalls = true)

        every {
            mother["feedGood"]()
        } returns "not bad"

        val result = mother.shopping(1, 1, 1)
        Assert.assertEquals("not bad", result)
        verify {
            mother.shopping(any(), any(), any())
            mother["feedGood"]()
        }
    }

    @Test
    fun testSingletonPrivateFunc() {
        mockkObject(UtilKotlinSingleton, recordPrivateCalls = true)
        UtilKotlinSingleton.call1()
        verify {
//            UtilKotlinSingleton invoke "call2" withArguments listOf("")
            UtilKotlinSingleton["call2"](any<String>())
        }
    }

    @Test
    fun testPrivateField() {
        val mother = spyk(Mother(), recordPrivateCalls = true)

//        every {
//            mother getProperty "age"
//        } returns 30

//        every {
//            mother setProperty "age" value less(20)
//        } just Runs

        justRun {
            mother invokeNoArgs "feedGood"
        }

        every {
            mother invoke "buy" withArguments listOf("food")
        } returns 100

//        mother.setAge(10)
//        Assert.assertEquals(30, mother.getAge())
        val result = mother.invokeBuy("food")
        Assert.assertEquals(100, result)

        verify {
            mother getProperty "age"
//            mother setProperty "age" value less(20)
            mother invoke "buy" withArguments listOf("food")
        }
    }

    @Test
    fun testFiled() {
        val mother = spyk(Mother(), recordPrivateCalls = true)

        every {
            mother setProperty "name" value any<String>()
        } propertyType String::class answers {
            fieldValue = "Jimmy"
        }
        mother.name = "lucy"
        Assert.assertEquals("Jimmy", mother.name)

        every {
            mother getProperty "name"
        } returns "women"
        Assert.assertEquals("women", mother.name)

        every {
            mother.name
        } answers {
            //或者
            // } propertyType String::class answers {
            "I am $fieldValue"
        } andThenAnswer {
            //后续的调用均返回该值
            "hello $fieldValue"
        }
        Assert.assertEquals("I am Jimmy", mother.name)
        Assert.assertEquals("hello Jimmy", mother.name)
    }

    @Test
    fun testMultipleInterfaces() {
        val spy = spyk(System.out, moreInterfaces = *arrayOf(Runnable::class))
        spy.println("hello")

        every {
            (spy as Runnable).run()
        } answers {
            (self as PrintStream).println("Run!")
        }

        val thread = Thread(spy as Runnable)
        thread.start()
        thread.join()
    }

    @Test(expected = Exception::class)
    fun testNothing() {
        val mother = spyk<Mother>()
        every {
            mother.quit(1)
        } throws Exception("test")

        mother.quit(1)
    }

    @Test
    fun testTimeOut() {
        mockk<Mother>() {
            every {
                giveMoney()
            } returns 20

            Thread {
                Thread.sleep(200)
                giveMoney()
            }.start()

            verify(timeout = 300) {
                giveMoney()
            }
        }
    }

    @After
    fun clear() {
        unmockkAll()
        clearAllMocks()
    }

    interface ClsWithManyMany {
        fun manyMany(vararg x: Any): Int
    }


    interface AddressBook {
        val contacts: List<Contact>
    }

    interface Contact {
        val name: String
        val telephone: String
        val address: Address
    }

    interface Address {
        val city: String
        val zip: String
    }

}