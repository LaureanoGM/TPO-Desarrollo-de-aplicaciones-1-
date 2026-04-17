package com.example.myapplication

import com.example.myapplication.model.MovieValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieValidatorTest {

    @Test
    fun `isValidTitle returns false for blank title`() {
        assertFalse(MovieValidator.isValidTitle(""))
        assertFalse(MovieValidator.isValidTitle("   "))
    }

    @Test
    fun `isValidTitle returns true for non-blank title`() {
        assertTrue(MovieValidator.isValidTitle("Inception"))
    }

    @Test
    fun `isValidReleaseYear returns false for year before 1888`() {
        assertFalse(MovieValidator.isValidReleaseYear(1800))
    }

    @Test
    fun `isValidReleaseYear returns true for valid year`() {
        assertTrue(MovieValidator.isValidReleaseYear(2024))
    }

    @Test
    fun `isValidRating returns false for rating out of range`() {
        assertFalse(MovieValidator.isValidRating(-1.0))
        assertFalse(MovieValidator.isValidRating(11.0))
    }

    @Test
    fun `isValidRating returns true for valid rating`() {
        assertTrue(MovieValidator.isValidRating(8.5))
        assertTrue(MovieValidator.isValidRating(0.0))
        assertTrue(MovieValidator.isValidRating(10.0))
    }
}
