package com.ariffadlysiregar.watchlist.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestCoroutineDispatcherRule(
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) :
    TestWatcher() {

    init {
        Dispatchers.setMain(testDispatcher)
    }

    fun runBlockingTest(block: (() -> Unit)) {
        testDispatcher.runBlockingTest { block() }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}