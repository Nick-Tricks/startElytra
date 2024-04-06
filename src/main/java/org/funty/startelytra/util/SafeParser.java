package org.funty.startelytra.util;

import java.util.function.Consumer;

public class SafeParser {
    public static double safeParseDouble(String string, double def, Consumer<Exception> numberFormatOrNPE) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException | NullPointerException e) {
            numberFormatOrNPE.accept(e);
            return def;
        }
    }

}
