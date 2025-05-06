package com.devsuperior.dscatalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devsuperior.dscatalog.projections.IdProjection;

public class Utils {

	public static <ID> List<? extends IdProjection<ID>> replace(List<? extends IdProjection<ID>> ordered,
			List<? extends IdProjection<ID>> unordered) {

		Map<ID, IdProjection<ID>> map = new HashMap<>();
		for (IdProjection<ID> u : unordered) {
			map.put(u.getId(), u);
		}

		List<IdProjection<ID>> result = new ArrayList<>();

		for (IdProjection<ID> o : ordered) {
			result.add(map.get(o.getId()));
		}

		return result;
	}

}
