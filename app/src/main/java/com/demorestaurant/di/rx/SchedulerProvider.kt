package com.demorestaurant.di.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun single(): Scheduler
    fun newThread(): Scheduler
}