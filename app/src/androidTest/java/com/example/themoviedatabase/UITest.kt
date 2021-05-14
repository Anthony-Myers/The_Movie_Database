package com.example.themoviedatabase

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class UITest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isMovieRecyclerInView() {
        onView(withId(R.id.loading_container)).check(matches(isDisplayed()))
    }

    /*@Test
    fun test_visibility_textInputEditText() {
        onView(withId(R.id.text_edit_text)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_spinner_recyclerView() {
        onView(withId(R.id.text_edit_text)).perform(typeText("Mike"), closeSoftKeyboard())

        onView(withId(R.id.spinner)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        SystemClock.sleep(1000)

        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
    }*/

}