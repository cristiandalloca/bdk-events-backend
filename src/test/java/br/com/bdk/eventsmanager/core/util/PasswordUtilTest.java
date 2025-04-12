package br.com.bdk.eventsmanager.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilTest {

    @Test
    void shouldReturnDefaultCharacterRulesForPassword() {
        var rules = PasswordUtil.getDefaultCharacterRules();
        assertThat(rules).hasSize(4);
    }

    @Test
    void shouldGenerateRandomPassword() {
        var randomPassword = PasswordUtil.generateRandomPassword();
        assertThat(randomPassword).isNotNull();
    }

    @Test
    void shouldReturnDefaultRolesForPassword() {
        var rules = PasswordUtil.getDefaultRules();
        assertThat(rules).hasSize(6);
    }
}