package edu.tacoma.uw.equipmentrental;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Random;

import edu.tacoma.uw.equipmentrental.authenticate.SignInActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class SignInActivityText {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    @Test
    public void testLogin() {
        Random random = new Random();
        //Generate an email address
        String email = "rabingora001@gmail.com";
//                "email" + (random.nextInt(10000) + 1)
//                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
//                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
//                + "@uw.edu";

        // Type text and then press the button.
        onView(withId(R.id.email_address_id))
                .perform(typeText(email));
        onView(withId(R.id.password_id))
                .perform(typeText("test123@"));
        onView(withId(R.id.login_button))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())        //i added to scroll to the button so that the keyboard does not come in middle
                .perform(click());

        onView(withText(email))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testLoginInvalidEmail() {
        // Type text and then press the button.
        onView(withId(R.id.email_address_id))
                .perform(typeText("rabin001.edu"));
        onView(withId(R.id.password_id))
                .perform(typeText("test1@#"));

        onView(withId(R.id.login_button))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())    //two lines i added to scroll to the button so that the keyboard does not come in middle
                .perform(click());

        onView(withText("Enter valid email address"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void testRegisterInvalidPassword() {
        // Type text and then press the button.
        onView(withId(R.id.email_address_id))
                .perform(typeText("rabin001@uw.edu"));
        onView(withId(R.id.password_id))
                .perform(typeText(""));
        onView(withId(R.id.login_button))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())        //i added to scroll to the button so that the keyboard does not come in middle
                .perform(click());

        onView(withText("Enter valid password (at least 6 characters)"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
}
