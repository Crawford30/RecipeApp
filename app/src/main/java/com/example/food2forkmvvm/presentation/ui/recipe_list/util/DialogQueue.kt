package com.example.food2forkmvvm.presentation.ui.recipe_list.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.food2forkmvvm.presentation.components.GenericDialogInfo
import com.example.food2forkmvvm.presentation.components.PositiveAction
import java.util.LinkedList
import java.util.Queue

class DialogQueue {

    //Note:: We need to use it in each of out view mdodels

    // Queue for "First-In-First-Out" behavior
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())


    fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {

            val update = LinkedList(queue.value)
            update.remove() // remove first (oldest message)
            queue.value = update

//            val update = queue.value
//            update.remove() // remove first (oldest message)
//            queue.value = LinkedList(update)
////            queue.value = LinkedList() //initialise an new linkedlist to force recompose when the queue is updated
//            //ArrayDeque() // force recompose (bug?)
//            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.value.offer(
            //GenericDialogInfo to build the datastructure
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeHeadMessage) //when we click ok, we want to remove the message on the head
                .description("description")
                .positive(
                    PositiveAction(
                        positiveBtnTxt = "Ok",
                        onPositiveAction = this::removeHeadMessage,//when we click ok, we want to remove the message on the head
                    )
                )
                .build()
        )
    }
}