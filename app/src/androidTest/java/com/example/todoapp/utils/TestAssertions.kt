package com.example.todoapp.utils

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.example.todoapp.data.local.entity.TodoNote

fun ComposeTestRule.assertTextDisplayed(text: String) {
    this.onNodeWithText(text).assertIsDisplayed()
}

fun ComposeTestRule.assertTextNotDisplayed(text: String) {
    this.onNodeWithText(text).assertDoesNotExist()
}

fun ComposeTestRule.assertNoteInList(note: TodoNote) {
    this.onNodeWithText(note.title).assertIsDisplayed()
}

fun ComposeTestRule.assertNoteNotInList(note: TodoNote) {
    this.onNodeWithText(note.title).assertDoesNotExist()
}

fun ComposeTestRule.assertSuccessMessage(message: String) {
    this.onNodeWithText(message).assertIsDisplayed()
}

fun ComposeTestRule.assertErrorMessage(message: String) {
    this.onNodeWithText(message).assertIsDisplayed()
}

fun ComposeTestRule.assertEmptyStateDisplayed() {
    this.assertTextDisplayed("No notes yet")
    this.assertTextDisplayed("Create your first note to get started")
}

fun ComposeTestRule.assertNoteTitleDisplayed(note: TodoNote) {
    this.onNodeWithText(note.title).assertIsDisplayed()
}

fun ComposeTestRule.waitForText(text: String, timeoutMillis: Long = 5000) {
    this.waitUntil(timeoutMillis) {
        try {
            this.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}

fun ComposeTestRule.countElementsWithContentDescription(
    contentDescription: String,
    useUnmergedTree: Boolean = true
): Int {
    return try {
        this.onAllNodesWithContentDescription(contentDescription, useUnmergedTree)
            .fetchSemanticsNodes()
            .size
    } catch (e: Exception) {
        0
    }
}

fun ComposeTestRule.countNoteCards(): Int {
    // Each note card has exactly one star icon (either "Add star" or "Remove star")
    // We count both to get the total number of cards
    val addStarCount = countElementsWithContentDescription("Add star", useUnmergedTree = true)
    val removeStarCount = countElementsWithContentDescription("Remove star", useUnmergedTree = true)
    return addStarCount + removeStarCount
}


fun ComposeTestRule.assertNoNoteCards() {
    val cardCount = this.countNoteCards()
    assert(cardCount == 0) {
        "Expected 0 note cards, but found $cardCount"
    }
}
