package com.szenamartin.android.vehicle.map


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.szenamartin.android.vehicle.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MapShownAfterPermission {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MapActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION"
        )

    @Test
    fun mapShownAfterPermission() {
        val view = onView(
            allOf(
                withContentDescription("Google Map"),
                withParent(withParent(withId(R.id.map))),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))
    }
}
