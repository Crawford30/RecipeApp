package com.example.food2forkmvvm.util

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {

    private var snackbarJob: Job? = null

    init {
        //if this is re-created, we create it
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {
        if (snackbarJob == null) {
            //create the job
            snackbarJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
                //Call it inside the coroutine
                cancelActiveJob()
            }

        } else {
            //if there is active job, cancel it before showing
            cancelActiveJob()

            snackbarJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
                cancelActiveJob()
            }

        }
    }

    private fun cancelActiveJob() {
        snackbarJob?.let { job ->
            job.cancel() //cancel the old job and create a new one
            snackbarJob = Job()
        }
    }
}