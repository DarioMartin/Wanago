package com.architectcoders.wanago.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.architectcoders.wanago.R
import com.architectcoders.wanago.presentation.MainActivity
import com.architectcoders.wanago.server.MockWebServerRule
import com.architectcoders.wanago.server.fromJson
import com.architectcoders.wanago.ui.OkHttp3IdlingResource.Companion
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("ticket_master_page_0_mock_response.json"),
        )
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("ticket_master_page_1_mock_response.json"),
        )
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("ticket_master_event_by_id_mock_response.json"),
        )
        hiltRule.inject()

        val resource = Companion.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun click_an_event_navigates_to_detail() {
        onView(withId(R.id.recyclerEventsList))
            .perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()
            )
        )
        onView(withId(R.id.details_event_name))
            .check(matches(withText("Cuidading Liren Masterclass")))
    }
}
