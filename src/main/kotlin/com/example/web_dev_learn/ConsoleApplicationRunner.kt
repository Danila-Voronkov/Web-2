package com.example.web_dev_learn

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConsoleApplicationRunner(
    private val todoController: ToDoController
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val scanner = Scanner(System.`in`)
        while (true) {
            println("Тудушка")
            println("1. Показать все")
            println("2. Вывести по ID")
            println("3. Создать")
            println("4. Изменить")
            println("5. Удалить")
            println("6. Посхалочка")
            print("Выберите: ")

            when (scanner.nextLine().toInt()) {
                1 -> listTodos()
                2 -> getTodoById(scanner)
                3 -> createTodo(scanner)
                4 -> editTodo(scanner)
                5 -> deleteTodoById(scanner)
                6 -> {
                    println("All your base are belong to us")
                    return
                }
                else -> println("Ошибка.")
            }
        }
    }

    private fun listTodos() {
        val todos = todoController.getTodos()
        todos.forEach { println(it) }
    }

    private fun getTodoById(scanner: Scanner) {
        print("Введите ID: ")
        val id = scanner.nextLine().toLong()
        val todo = todoController.getTodo(id)
        println(todo)
    }

    private fun createTodo(scanner: Scanner) {
        print("Enter title: ")
        val title = scanner.nextLine()
        print("Enter description: ")
        val description = scanner.nextLine()

        val todoRequestBody = TodoItemRequestBody(title, description, emptyList())
        val todo = todoController.createTodo(todoRequestBody)
        println("Todo created: $todo")
    }

    private fun editTodo(scanner: Scanner) {
        print("Enter Todo ID: ")
        val id = scanner.nextLine().toLong()
        print("Enter new title: ")
        val title = scanner.nextLine()
        print("Enter new description: ")
        val description = scanner.nextLine()

        val todoRequestBody = TodoItemRequestBody(title, description, emptyList())
        val todo = todoController.editTodo(todoRequestBody, id)
        println("Todo edited: $todo")
    }

    private fun deleteTodoById(scanner: Scanner) {
        print("Enter Todo ID: ")
        val id = scanner.nextLine().toLong()
        todoController.deleteTodo(id)
        println("Todo deleted.")
    }
}