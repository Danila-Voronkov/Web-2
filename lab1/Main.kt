package org.example

import Node
import kotlin.random.Random

fun main() {
    print("Введите ваш номер телефона: ")
    MobilePhone(readLine()).apply {
        while(true){
            println("--------------------------------------\n" +
                    "Выбирите действие:\n" +
                    "1) Добавить новый контакт\n" +
                    "2) Изменить существующий контакт\n" +
                    "3) Удалить существующий контакт\n" +
                    "4) Показать все контакты\n" +
                    "5) Выход")
            var userAction = readLine()
            if (userAction == "1"){

                print("Введите имя: ")
                var newName = readLine()

                print("Введите номер: ")
                var newNumber = readLine()

                addNewContact(Contact(newName, newNumber))

            } else if (userAction == "2") {

                print("Введите имя изменяемого: ")
                var updateName = queryContact(readLine())

                if (updateName != null){
                    print("Введите новое имя: ")
                    var newName = readLine()
                    print("Введите новый номер: ")
                    var newNumber = readLine()

                    updateContact(updateName ,Contact(newName, newNumber))
                } else {
                    println("Ошибка")
                }

            } else if (userAction == "3") {

                print("Введите имя: ")
                var removeName = queryContact(readLine())
                if (removeName != null){
                    removeContact(removeName)
                } else {
                    println("Ошибка")
                }

            } else if (userAction == "4") {
                printContacts()
            } else if (userAction == "5") {
                break
            } else {
                println("Ошибка")
            }
        }

    }

    val newTree = Node(2).apply {
        insert(5)
        insert(1)
        insert(9)
        insert(4)
        println(this)
        delete(1)
        println(this)
    }
}

class MobilePhone(myNumber: String?) {
    private val myContacts = mutableListOf(Contact("MY NUMBER", myNumber))

    fun addNewContact(newContact: Contact) = myContacts.add(newContact)

    fun updateContact(oldContact: Contact?, newContact: Contact): Boolean {
        val isUpdated = myContacts.removeIf {
            it == oldContact
        }
        if (isUpdated) myContacts.add(newContact)
        return isUpdated
    }

    fun removeContact(contact: Contact?) = myContacts.remove(contact)

    fun findContact(contact: Contact): Int = myContacts.indexOf(contact)

    fun queryContact(query: String?): Contact? = myContacts.firstOrNull { it.name == query }

    fun printContacts() = myContacts.forEach(::println)
}

//data class Contact(val name: String?, val number: String?, val id: Int = Random.nextInt())
data class Contact(val name: String?, val number: String?)