/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.mikeslab.util.language;

/**
 * The enum Recognized languages.
 */
public enum RecognizedLanguages {
    en_US,
    it_IT,
    es_ES,
    fr_FR;

    /**
     * Is recognized language boolean.
     *
     * @param language the language
     * @return the boolean
     */
    public static boolean isRecognizedLanguage(String language) {
        for(RecognizedLanguages recognizedLanguage : RecognizedLanguages.values()) {
            if(recognizedLanguage.name().equals(language)) {
                return true;
            }
        }

        return false;
    }
}
