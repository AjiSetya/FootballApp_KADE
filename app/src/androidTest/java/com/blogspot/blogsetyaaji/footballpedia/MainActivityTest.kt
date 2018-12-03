package com.blogspot.blogsetyaaji.footballpedia


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.blogspot.blogsetyaaji.footballpedia.R.id.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        onView(withId(nav_match)).check(ViewAssertions.matches(isDisplayed()))

        Thread.sleep(6000)
        onView(withId(lv_match))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(lv_match)).perform(
            RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(2)
        )
        onView(withId(lv_match)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        Thread.sleep(6000)
        onView(withId(add_to_favorite))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
        pressBack()

        Thread.sleep(3000)
        onView(withId(pagerMatch)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(pagerMatch)).perform(swipeLeft())

        Thread.sleep(6000)
        onView(withId(lv_matchNext))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(lv_matchNext)).perform(
            RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(2)
        )
        onView(withId(lv_matchNext)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        Thread.sleep(6000)
        onView(withId(add_to_favorite))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
        pressBack()

        Thread.sleep(6000)
        onView(withId(nav_team)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(nav_team)).perform(click())
        Thread.sleep(3000)

        Thread.sleep(6000)
        onView(withId(lv_team))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(lv_team)).perform(
            RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(2)
        )
        onView(withId(lv_team)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        Thread.sleep(6000)
        onView(withId(add_to_favorite))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(3000)
        pressBack()

        Thread.sleep(6000)
        onView(withId(nav_favorites)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(nav_favorites)).perform(click())
        Thread.sleep(3000)
        onView(withId(pagerTeam)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(pagerTeam)).perform(swipeLeft())
        Thread.sleep(3000)

    }
}
