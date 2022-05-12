package com.project.notesapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.project.notesapp.encoder_decoder.PasswordEncoderDecoder;
import com.project.notesapp.entity.Note;
import com.project.notesapp.entity.User;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void customTests() {
        try {
            User user = new User("Aniket", "7746077984", "aniketv2000@gmail.com", PasswordEncoderDecoder.encrypt("Aniket@123"));
            assertEquals(user.getName(), "Name");
            assertEquals(user.getMobile(), "7746077984");
            assertEquals(user.getEmail(), "aniketv2000@gmail.com");
            assertEquals(PasswordEncoderDecoder.decrypt(user.getPassword()), "Aniket@123");

            Note note = new Note("Title","7746077984","Description");
            assertNotEquals(note.getTitle(), "Name");
            assertEquals(note.getMobile(), "7746077984");
            assertEquals(note.getDescription(), "Description");
            assertEquals(note.getImageList().length(),0);

        } catch (Exception e) {
            e.printStackTrace();
        }

//
    }
}