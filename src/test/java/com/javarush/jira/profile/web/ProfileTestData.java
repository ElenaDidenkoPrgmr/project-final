package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Contact;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ProfileTestData {
    public static final MatcherFactory.Matcher<Profile> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            Profile.class, "lastLogin", "lastFailedLogin");

    public static final MatcherFactory.Matcher<ProfileTo> TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class, "lastLogin");

    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final long USER_ID = 1;
    public static final long ADMIN_ID = 2;

    public static final long USER_MASK_NOTIFICATION = 49L;
    public static final long ADMIN_MASK_NOTIFICATION = 14L;


    public static final Set<ContactTo> ADMIN_CONTACT_TO = new HashSet<>(Arrays.asList(
            new ContactTo("github", "adminGitHub"),
            new ContactTo("vk", "adminVk"),
            new ContactTo("tg", "adminTg")));

    public static final Set<ContactTo> USER_CONTACT_TO = new HashSet<>(Arrays.asList(
            new ContactTo("skype", "userSkype"),
            new ContactTo("mobile", "+01234567890"),
            new ContactTo("website", "user.com")));

    public static final long UPDATE_USER_MASK_NOTIFICATION = 14L;


    public static final Set<ContactTo> UPDATE_USER_CONTACT_TO = new HashSet<>(Arrays.asList(
            new ContactTo("skype", "updateUserSkype"),
            new ContactTo("mobile", "+00987654321"),
            new ContactTo("website", "updateUser.com")));

    public static final Set<ContactTo> UPDATE_USER_CONTACT_INVALID_TO = new HashSet<>(Arrays.asList(
            new ContactTo("skype", ""),
            new ContactTo("mobile", ""),
            new ContactTo("website", "")));

    public static final ProfileTo USER_PROFILE_TO = new ProfileTo(USER_ID,
            ProfileUtil.maskToNotifications(USER_MASK_NOTIFICATION),
            USER_CONTACT_TO);

    public static final ProfileTo ADMIN_PROFILE_TO = new ProfileTo(ADMIN_ID,
            ProfileUtil.maskToNotifications(ADMIN_MASK_NOTIFICATION),
            ADMIN_CONTACT_TO);

    public static ProfileTo getUpdated() {
        ProfileTo to =  new ProfileTo(USER_ID,
                ProfileUtil.maskToNotifications(UPDATE_USER_MASK_NOTIFICATION),null);

        for (ContactTo contact:UPDATE_USER_CONTACT_TO){
            contact.setId(USER_ID);
        }

        to.setContacts(UPDATE_USER_CONTACT_TO);
        return to;
    }

    public static ProfileTo getUpdatedInvalid() {
        ProfileTo to =  new ProfileTo(USER_ID,
                ProfileUtil.maskToNotifications(UPDATE_USER_MASK_NOTIFICATION),null);

        for (ContactTo contact:UPDATE_USER_CONTACT_INVALID_TO){
            contact.setId(USER_ID);
        }

        to.setContacts(UPDATE_USER_CONTACT_INVALID_TO);
        return to;
    }
}
