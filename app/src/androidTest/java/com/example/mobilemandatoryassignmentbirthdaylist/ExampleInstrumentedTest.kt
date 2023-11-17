package com.example.mobilemandatoryassignmentbirthdaylist


import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.regex.Pattern.matches

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mobilemandatoryassignmentbirthdaylist", appContext.packageName)
    }
    @Test
    fun testRecyclerViewIsDisplayed() {
        val recyclerViewId = R.id.recyclerView

       onView(withId(recyclerViewId)).check(matches(isDisplayed()))

    }
}
