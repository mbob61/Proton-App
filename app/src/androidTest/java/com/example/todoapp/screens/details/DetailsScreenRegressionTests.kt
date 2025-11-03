package com.example.todoapp.screens.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.example.todoapp.annotations.RegressionTest
import com.example.todoapp.base.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Regression tests for the Details Screen.
 */
@HiltAndroidTest
@RegressionTest
class DetailsScreenRegressionTests : BaseTest() {

    @Test
    fun tc010_editAndDiscardChanges() {
        // TC-010: Edit note and discard changes
        // Create a test note
        runBlocking {
            insertTestNote(
                title = "Original Title",
                content = "Original content"
            )
        }

        // Wait until the note appears in the list
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Original Title", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Navigate to note details
        composeTestRule.onNodeWithText("Original Title", useUnmergedTree = true)
            .performClick()

        composeTestRule.waitForIdle()

        // Modify title and content
        composeTestRule.onNodeWithText("Original Title")
            .performTextReplacement("Updated Title")

        composeTestRule.onNodeWithText("Original content")
            .performTextReplacement("Updated content")

        composeTestRule.waitForIdle()

        // Tap back arrow
        composeTestRule.onNodeWithContentDescription("Back")
            .performClick()

        composeTestRule.waitForIdle()

        // Verify discard dialog appears
        composeTestRule.onNodeWithText("Discard Changes?").assertIsDisplayed()

        // Tap "Discard"
        composeTestRule.onNodeWithText("Discard").performClick()

        composeTestRule.waitForIdle()

        // Verify navigation back to list and that the original content is unchanged
        composeTestRule.onNodeWithText("Original Title", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Original content", useUnmergedTree = true)
            .assertIsDisplayed()

        // Also verify the updated content is not present
        composeTestRule.onNodeWithText("Updated Title", useUnmergedTree = true)
            .assertDoesNotExist()
    }
}
