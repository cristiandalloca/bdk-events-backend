package br.com.bdk.eventsmanager.core.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordGenerator;
import org.passay.Rule;
import org.passay.WhitespaceRule;

import java.util.ArrayList;
import java.util.List;

public class PasswordUtil {

    private PasswordUtil() {
    }

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;


    public static List<Rule> getDefaultRules() {
        List<Rule> rules = new ArrayList<>(List.of(new LengthRule(MIN_LENGTH, MAX_LENGTH), new WhitespaceRule()));
        rules.addAll(getDefaultCharacterRules());
        return rules;
    }

    public static List<CharacterRule> getDefaultCharacterRules() {
        return List.of(
                //At least one upper case letter
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                //At least one lower case letter
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                //At least one number
                new CharacterRule(EnglishCharacterData.Digit, 1),

                //At least one special characters
                new CharacterRule(EnglishCharacterData.Special, 1));
    }

    public static String generateRandomPassword() {
        List<CharacterRule> defaultCharacterRules = List.of(
                //At least one upper case letter
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                //At least one lower case letter
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                //At least one number
                new CharacterRule(EnglishCharacterData.Digit, 1));
        return new PasswordGenerator().generatePassword(MIN_LENGTH, defaultCharacterRules);
    }
}
