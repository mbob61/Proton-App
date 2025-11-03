package com.example.todoapp.screens.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.todoapp.annotations.RegressionTest
import com.example.todoapp.base.BaseTest
import com.example.todoapp.utils.assertTextDisplayed
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

/**
 * Regression tests for the List Screen.
 */
@HiltAndroidTest
@RegressionTest
class ListScreenRegressionTests : BaseTest() {

    @Test
    fun tc002_createNoteViaEmptyStateButton() {
        // TC-002: Create note via empty state button
        composeTestRule.waitForIdle()

        // Verify empty state is displayed and tap the create button
        composeTestRule.assertTextDisplayed("No notes yet")
        composeTestRule.onNodeWithText("Create Note").performClick()

        composeTestRule.waitForIdle()

        // Fill in the title and content
        composeTestRule.onNodeWithText("Note Title")
            .performTextInput("Empty State Note")

        composeTestRule.onNodeWithText("Write your note here...")
            .performTextInput("Content from empty state")

        // Save the note
        composeTestRule.onNodeWithContentDescription("Save note")
            .performClick()

        composeTestRule.waitForIdle()

        // Verify the new note is displayed on the list screen
        composeTestRule.onNodeWithText("Empty State Note", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun tc006_verifyEmptyState() {
        // TC-006: Verify that the empty state UI is shown when no notes exist.
        // This test aligns with TC-006 from TEST_CASES.md.
        composeTestRule.waitForIdle()

        // Assert that the empty state texts are displayed
        composeTestRule.assertTextDisplayed("No notes yet")
        composeTestRule.assertTextDisplayed("Create your first note to get started")
    }
}
