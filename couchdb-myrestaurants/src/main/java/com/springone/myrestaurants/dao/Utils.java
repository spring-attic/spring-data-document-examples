package com.springone.myrestaurants.dao;

import java.util.ArrayList;
import java.util.List;

public class Utils {


	public static List<Integer> convertToList(int[] favorites) {
		List<Integer> list = new ArrayList<Integer>(favorites.length);
		for (int i = 0; i < favorites.length; i++) {
			list.add(favorites[i]);
		}
		return list;
	}
	
	public static int[] convertToPrimArray(List<Integer> list) {
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
