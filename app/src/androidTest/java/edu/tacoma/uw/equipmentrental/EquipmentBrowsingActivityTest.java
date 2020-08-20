package edu.tacoma.uw.equipmentrental;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.tacoma.uw.equipmentrental.authenticate.SignInActivity;
import edu.tacoma.uw.equipmentrental.main.EquipmentBrowsingActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EquipmentBrowsingActivityTest {

    @Rule
    public ActivityTestRule<EquipmentBrowsingActivity> mActivityRule = new ActivityTestRule<>(
            EquipmentBrowsingActivity.class);


    @Test
    public void displaysRecyclerView() {
        // First, scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = "shovel";
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }

    @Test
    public void floatingActionButtonWorks() {
        onView(withId(R.id.fab))
                .perform(click());
        onView(withText("ADD RENTAL EQUIPMENT"))
                .check(matches(isDisplayed()));
    }


}
