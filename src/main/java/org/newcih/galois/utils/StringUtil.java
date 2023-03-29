/*
 * MIT License
 *
 * Copyright (c) [2023] [$user]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.newcih.galois.utils;

/**
 * string util
 *
 * @author liuguangsheng
 * @since 1.0.0
 */
public class StringUtil {

    /**
     * is blank
     *
     * @param str str
     * @return {@link boolean}
     */
    public static boolean isBlank(String str) {
        return isEmpty(str) || "".equals(str.trim());
    }

    /**
     * is empty
     *
     * @param str str
     * @return {@link boolean}
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * is not blank
     *
     * @param str str
     * @return {@link boolean}
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * is not empty
     *
     * @param str str
     * @return {@link boolean}
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
