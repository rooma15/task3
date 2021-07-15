package com.epam.esm.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleTranslator {
  public static String translate(String message) {
    return ResourceBundle.getBundle("messages", Locale.getDefault()).getString(message);
  }

  public static String translate(String message, Locale locale) {
    return ResourceBundle.getBundle("messages", locale).getString(message);
  }
}
