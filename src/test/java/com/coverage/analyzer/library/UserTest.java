package com.coverage.analyzer.library;

import com.coverage.analyzer.library.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    User testee;

    @Before
    public void setUp() throws Exception {
        testee = new User();
    }

    @Test
    public void testAppendMessage_givenArrayIsEmpty_whenAppendMessageIsCalled_thenReturnTrue() {

        final boolean result = testee.appendMessage("supriya");

        Assert.assertTrue(result == true);
        final String[] messages = testee.getMessages();
        Assert.assertTrue(messages[0] == "supriya");
    }

    @Test
    public void testAppendMessage_givenTheArrayHas1Element_whenAppendMessage_thenReturnTrue_andArrayHas2Elements() {
        testee.appendMessage("Supriya");

        final boolean message = testee.appendMessage("snigdha");

        Assert.assertTrue(message == true);
        final String[] messages = testee.getMessages();
        Assert.assertTrue(messages[1] == "snigdha");
    }

    @Test
    public void testAppendMessage_addElementsIfArrayIsFull_returnFalse() {
        testee.appendMessage("supriya");
        testee.appendMessage("snigdha");

        final boolean  message =testee.appendMessage("biswas");

        Assert.assertTrue(message == false);

    }

    @Test
    public void givenAValidUserId_havingMessages_returnAllMessagesForTheUser(){
        testee.setUserId(1);
        testee.appendMessage("supriya");
        testee.appendMessage("snigdha");
        String expectedMessage = "User Id: 1 Messages: supriya,snigdha";

        String result = testee.getAllMessagesByUserId(1);

        Assert.assertEquals(expectedMessage, result);
    }

    @Test
    public void givenInvalidUserId_returnsUserIdNotfound(){
        String expectedMessage = "User Id: 6 not found";

        final String result= testee.getAllMessagesByUserId(6);

        Assert.assertEquals(expectedMessage,result);
    }

    @Test
    public void givenAValidUserId_withNoMessages_returnsNoMessagesFoundForTheUser(){
        testee.setUserId(1);
        String expected = "User Id: 1 Messages: No Messages Found";

        final String message = testee.getAllMessagesByUserId(1);
        Assert.assertEquals(expected,message);
    }
}