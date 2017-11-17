/*
 * Copyright (C) 2015 The Android Open Source Project
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

package ch.deletescape.lawnchair.allapps;

import java.util.HashMap;
import java.util.List;

import ch.deletescape.lawnchair.AppInfo;
import ch.deletescape.lawnchair.LauncherAppState;
import ch.deletescape.lawnchair.util.Pinyin;
import ch.deletescape.lawnchair.util.UnicodeFilter;

/**
 * A search algorithm that changes every non-ascii characters to theirs ascii equivalents and
 * then performs comparison.
 */
public class UnicodeStrippedAppSearchAlgorithm extends DefaultAppSearchAlgorithm {
    public UnicodeStrippedAppSearchAlgorithm(List<AppInfo> apps, HashMap<String, Pinyin> appsPinYinMap) {
        super(apps, appsPinYinMap);
    }

    @Override
    protected boolean matches(AppInfo info, String query) {
        if (info.componentName.getPackageName().equals(LauncherAppState.getInstanceNoCreate().getContext().getPackageName()))
            return false;

        String title = UnicodeFilter.filter(info.title.toString().toLowerCase());
        Pinyin pinyin = mAppsPinYinMap.get(info.componentName.getPackageName());
        String strippedQuery = UnicodeFilter.filter(query.trim());
        int queryLength = strippedQuery.length();

        if (title.length() < queryLength && title.length() == pinyin.pinyinLong.length() || queryLength <= 0) {
            return false;
        }

        if (pinyin.pinyinShort.contains(Pinyin.normalize(query)) ||
                pinyin.pinyinLong.contains(Pinyin.normalize(query))) {
            return true;
        }

        return title.indexOf(strippedQuery) >= 0;
    }
}
