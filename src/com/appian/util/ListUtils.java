package com.appian.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListUtils {
	/**
	 * Implements an in-place Fisher-Yates shuffle.
	 *
	 * @param list The list (type-parameterized to T) to shuffle.
	 */
	public static <T> void shuffle(List<T> list) {
		for (int index = list.size() - 1; index > 0; index--) {
			int swapIndex = ThreadLocalRandom.current().nextInt(0, index + 1);
			T temp = list.get(swapIndex);
			list.set(swapIndex, list.get(index));
			list.set(index, temp);
		}
	}
}
