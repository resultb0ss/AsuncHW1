import kotlinx.coroutines.*
import kotlin.time.measureTime

suspend fun main() = runBlocking {

    val arrayRandomNumbers = getRandomListNum()
    val arrayRandomChars = getRandomListChar()

    val time = measureTime {

        val one = async {unpack(arrayRandomChars)}
        val two = async {unpack(arrayRandomNumbers)}
        val three = async(start = CoroutineStart.LAZY) { println(concatenate<Char,Int,String>(arrayRandomChars,arrayRandomNumbers).second )   }

        one.join()
        two.join()
        three.await()
        println("Общий размер ${one.await() + two.await()}")

    }
    println("Затрачено времени: $time")





}




fun getRandomListNum(): MutableList<Int>{
    val array: MutableList<Int> = arrayListOf()
    for (i in 1..10){
        val num = (1..100).random()
        array.add(num)
    }
    return array
}

fun getRandomListChar(): MutableList<Char>{
    val array: MutableList<Char> = arrayListOf()
    for (i in 1..10){
        val ch = ('a'..'z').random()
        array.add(ch)
    }
    return array
}

suspend fun <T> unpack(array: MutableList<T>): Int{
    for (i in array){
        println(i)
        delay(1000L)
    }
    println("Количество элементов ${array.size}")

    return array.size
}

fun <T,V,R> concatenate(arrayOne: MutableList<T>, arrayTwo: MutableList<V>): Pair<Int, MutableList<R>>{

    val myList: MutableList<R> = ArrayList()
    val conc = arrayOne.map { it.toString() } + arrayTwo.map { it.toString() }
    myList.addAll(conc.map { i -> i as R })
    val pairOne = Pair(arrayOne.size + arrayTwo.size,myList)
    return pairOne
}
