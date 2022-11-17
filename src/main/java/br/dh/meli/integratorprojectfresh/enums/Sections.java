package br.dh.meli.integratorprojectfresh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Sections lists the types and descriptions of warehouse sections.
 */
@Getter
@AllArgsConstructor
public enum Sections {
  FRESH("Fresh", "FS"),
  FROZEN("Frozen", "FF"),
  REFRIGERATED("Refrigerated", "RF");

  private final String name;
  private final String code;

  /**
   * getSectionByCode
   * Returns the section enum by its code.
   * @param code - the section code
   * @return Sections - the section enum
   */
  public static Sections getSectionByCode(String code) {
    for (Sections section : Sections.values()) {
      if (section.getCode().equals(code)) {
        return section;
      }
    }
    return null;
  }
}
