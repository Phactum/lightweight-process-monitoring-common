package com.phactum.lpm.application

import com.phactum.lpm.annotation.StaticSignal
import com.phactum.lpm.model.ProcessState

class Signal {
    companion object {
        @JvmStatic
        fun produce(processState: ProcessState, taskIds: List<String>) {
            when (processState) {
                ProcessState.STARTED -> startSignal(processState,taskIds)
                ProcessState.FINISHED -> finishedSignal(processState,taskIds)
                ProcessState.FAILED -> failedSignal(processState, taskIds)
            }
        }
        @StaticSignal
        private fun startSignal(processState: ProcessState, taskIds: List<String>){

        }
        @StaticSignal
        private fun finishedSignal(processState: ProcessState, taskIds: List<String>){

        }
        @StaticSignal
        private fun failedSignal(processState: ProcessState, taskIds: List<String>){

        }
    }
}