package com.seguridadtest.sanitizador;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SanitizationUtil {

    private static final PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public static String sanitize(String input) {

        if (input == null) {
            return null;
        } else {
            return policy.sanitize(input);
        }
    }
}
