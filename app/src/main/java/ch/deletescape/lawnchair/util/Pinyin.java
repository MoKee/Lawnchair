/*
 * Copyright (C) 2017 The MoKee Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.deletescape.lawnchair.util;

import android.text.TextUtils;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class Pinyin {

    public final String pinyinLong;

    public final String pinyinShort;

    private Pinyin(String[] source) {
        StringBuilder builderLong = new StringBuilder();
        StringBuilder builderShort = new StringBuilder();

        for (String s : source) {
            s = s.trim();
            if (TextUtils.isGraphic(s)) {
                builderLong.append(s);
                builderShort.append(s.charAt(0));
            }
        }

        this.pinyinLong = builderLong.toString();
        this.pinyinShort = builderShort.toString();
    }

    public static Pinyin from(String source) {
        return new Pinyin(PinyinHelper.convertToPinyinString(source, " ", PinyinFormat.WITHOUT_TONE)
                .toLowerCase()
                .split(" "));
    }

    public static String normalize(String keyword) {
        return keyword.toLowerCase().replace(" ", "");
    }

}