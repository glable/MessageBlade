/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.smallchill.system.meta.factory;

import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.meta.MetaIntercept;
import com.smallchill.core.meta.MetaManager;
import com.smallchill.system.meta.intercept.ParameterIntercept;
import com.smallchill.system.model.Parameter;

public class ParameterFactory extends MetaManager {

	public Class<? extends MetaIntercept> intercept() {
		return ParameterIntercept.class;
	}

	public String controllerKey() {
		return "parameter";
	}

	public String paraPrefix() {
		return getTableName(Parameter.class);
	}

	public Map<String, String> renderMap() {
		Map<String, String> renderMap = new HashMap<>();
		renderMap.put(KEY_INDEX, "/system/parameter/parameter.html");
		renderMap.put(KEY_ADD, "/system/parameter/parameter_add.html");
		renderMap.put(KEY_EDIT, "/system/parameter/parameter_edit.html");
		renderMap.put(KEY_VIEW, "/system/parameter/parameter_view.html");
		return renderMap;
	}

	public Map<String, String> sourceMap() {
		Map<String, String> sourceMap = new HashMap<>();
		sourceMap.put(KEY_INDEX, "parameter.sourceList");
		return sourceMap;
	}

}
