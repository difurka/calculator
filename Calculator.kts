
//112234567890 + 112234567890 * (10000000999 - 999)
import java.math.BigInteger


fun main() {
    val numbers = mutableMapOf<String, BigInteger>()
    while (true) {
        val message = readLine()!!
        if (message.equals("/exit")) {
            print("Bye!")
            break
        } else if (message.equals("/help")) {
            print("The program calculates the sum of numbers.")
        } else if (message.startsWith("/")) {
            print("Unknown command")
        } else {
            try {
                if ("=" in message) {
                    val arr = message.split("=")
                    if (arr[0].any { it in '0'..'9' } || arr[0].any { it in 'а'..'я' }  ) {
                        println("Invalid identifier")
                    } else if (arr.size == 2) {
                        if (numbers.containsKey(arr[1].trim())) {
                            numbers[arr[0].trim()] = numbers[arr[1].trim()]!!
                        } else if (arr[1].trim().all{it.isDigit()}) {
                            numbers[arr[0].trim()] =  BigInteger(arr[1].trim())
                        } else {
                            println("Invalid assignment")
                        }


                    } else {
                        println("Invalid assignment")
                    }

                } else if (" " in message) {
                    val arr1 = message.trim().split(" ")
                    val arr =  mutableListOf<String>()
                    var el1 =""
                    var el2 =""
                    var count = 0
                    // to simple string
                    for (el in arr1) {
                        if (el in numbers) {
                            arr.add(numbers[el].toString())
                        } else if (el.first() == '(') {
                            el2 = el

                            while (el2.first() == '('){
                                arr.add("(")
                                el2 = el2.substring(1)
                            }

                            if (el2 in numbers){

                                arr.add(numbers[el2].toString())
                            }else if (el2.all{it.isDigit()} ) {

                                arr.add(el2)
                            } else   {
                                println("Unknown variable")
                            }
                        } else if (el.last() == ')') {
                            el1 = el
                            while(el1.last() == ')'){
                                count += 1
                                el1 = el1.substring(0, el1.length - 1)
                            }
                            if (el1 in numbers){
                                arr.add(numbers[el1].toString())
                                for (i in 1..count) {arr.add(")")}
                                count = 0
                            } else if (el1.all{it.isDigit()} ) {

                                arr.add(el1)
                                for (i in 1..count) {arr.add(")")}
                                count = 0
                            } else  {
                                println("Unknown variable")
                            }
                        } else if (el.contains("+")) {
                            arr.add("+")
                        } else if (el.contains("-")) {
                            if (el.length % 2 == 0) {
                                arr.add("+")
                            } else {
                                arr.add("-")
                            }
                        } else if (el.trim().equals("*")) {
                            arr.add("*")
                        } else if (el.trim().equals("/")) {
                            arr.add("/")
                        } else if (el.trim().equals("(")) {
                            arr.add("(")
                        } else if (el.trim().equals(")")) {
                            arr.add(")")
                        } else if (el.trim().equals("^")) {
                            arr.add("^")
                        } else if (el.trim().all{it.isDigit()}) {
                            arr.add(el.trim())
                        } else {
                            println("Invalid assignment")

                        }


                    }
                    // to Polish Notation
                    val symb = mutableMapOf("-" to 1, "+" to 1, "*" to 2, "/" to 2, "^" to 3, "(" to 0, ")" to 0)
                    var vivod = mutableListOf<String>()
                    val steck = mutableListOf<String>()
                    for (el in arr) {
                        if (el == "(") {
                            steck.add(el)
                        } else if (el == ")") {
                            while (steck.last() != "(") {
                                vivod.add(steck.last())
                                steck.removeAt(steck.size - 1)
                            }
                            steck.removeAt(steck.size - 1)
                        }else if (el in symb.keys) {
                            if ( steck.isEmpty() ||  symb[el]!! > symb[steck.last()]!!) {
                                steck.add(el)
                            } else {
                                while (!steck.isEmpty() && symb[el]!! <= symb[steck.last()]!!){
                                    vivod.add(steck.last())
                                    steck.removeAt(steck.size - 1)
                                }
                                steck.add(el)
                            }
                        } else {
                            vivod.add(el)
                        }
                    }
                    while (steck.size > 0 ) {
                        vivod.add(steck[steck.size - 1])
                        steck.removeAt(steck.size - 1)
                    }

                    // to summ
                    val steck1 = mutableListOf<String>()
                    var a = BigInteger("0")
                    var b = BigInteger("0")
                    var c = BigInteger("0")
                    for (i in 0..vivod.size - 1) {

                        if (vivod[i] in symb) {
                            b = BigInteger(steck1.last())
                            a = BigInteger(steck1[steck1.size - 2])
                            steck1.removeAt(steck1.size - 1)
                            steck1.removeAt(steck1.size - 1)

                            when (vivod[i]) {
                                "+" -> c = a + b
                                "-" -> c = a - b
                                "*" -> c = a * b
                                "/" -> c = a / b
                                // "^" -> c = Math.pow(a.toDouble(), b.toDouble())
                            }
                            steck1.add(c.toString())

                        } else {
                            steck1.add(vivod[i])
                        }

                    }
                    println(steck1.last())
                } else if (message.equals("")) {
                    continue
                } else if (numbers.containsKey(message)) {
                    println(numbers[message])
                } else if (message.trim().any { it in 'a'..'z' } || message.trim().any { it in 'A'..'Z' }) {
                    println("Unknown variable")
                } else if (message.all{it.isDigit()}){
                    println(message)
                }
            } catch (e: Exception) {                          //сatch(e: NumberFormatException){
                println("Invalid expression")
            }
        }
    }
}
